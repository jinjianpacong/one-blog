package com.blog.springbootinit;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.blog.springbootinit.model.dto.post.PostEsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;


@SpringBootTest
public class SimpleCanalClientExample {
    @Resource
    private  ElasticsearchRestTemplate elasticsearchRestTemplate;

    public static void main(String args[]) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmptyCount = 120;
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }
    @Test
    public void test(){
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmptyCount = 120;
            while (true) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

//            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }
    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            String dataBaseName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));
            if(dataBaseName.equals("my_db")&&tableName.equals("post")){
                for (RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == EventType.DELETE) {
                        printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == EventType.INSERT) {
                        printColumn(rowData.getAfterColumnsList());
                    } else {
                        System.out.println("-------&gt; before");
                        printColumn(rowData.getBeforeColumnsList());
                        System.out.println("-------&gt; after");
                        printColumn(rowData.getAfterColumnsList());
                    }
                }
            }

        }
    }
    private static PostEsDTO columnsToPostDTO(List<Column> columns) throws ParseException {
        PostEsDTO postEsDTO = new PostEsDTO();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Column column:columns){
            switch (column.getName()){
                case "id":
                    postEsDTO.setId(Long.valueOf(column.getValue()));
                    break;
                case "title":
                    postEsDTO.setTitle(column.getName());
                    break;
                case "content":
                    postEsDTO.setContent(column.getValue());
                    break;
                case "tags":
                    JSONArray jsonArray = JSON.parseArray(column.getValue());
                    List<String> tags = jsonArray.stream().map(item -> (String) item).collect(Collectors.toList());
                    postEsDTO.setTags(tags);
                    break;
                case "userId":
                    postEsDTO.setUserId(Long.valueOf(column.getValue()));
                    break;
                case "createTime":
                    Date createTime = format.parse(column.getValue());
                    postEsDTO.setCreateTime(createTime);
                    break;
                case "updateTime":
                    Date updateTime = format.parse(column.getValue());
                    postEsDTO.setUpdateTime(updateTime);
                    break;
                case "isDelete":
                    postEsDTO.setIsDelete(Integer.valueOf(column.getValue()));
                    break;
            }

        }
        return postEsDTO;

    }
    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

}
