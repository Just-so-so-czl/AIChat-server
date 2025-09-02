package com.example.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.websocket.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AIChat {
    private static String apiKey = "sk-15e1da8dac5c41bd86b7926ab953c7fe";
    private static String baseUrl = "https://api.deepseek.com/chat/completions";


    public static void getAIReply(String content, Session session) throws IOException {
        // 构建请求JSON体
        JSONObject requestBody = new JSONObject();
        JSONArray messages = new JSONArray();

        // 添加系统角色消息
        JSONObject systemMsg = new JSONObject();
        systemMsg.put("role", "system");
        systemMsg.put("content", "You are a helpful assistant");
        messages.add(systemMsg);

        // 添加用户消息
        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", content);
        messages.add(userMsg);

        requestBody.put("messages", messages);
        requestBody.put("model", "deepseek-chat");
        requestBody.put("frequency_penalty", 0);
        requestBody.put("max_tokens", 4096);
        requestBody.put("presence_penalty", 0);

        // 响应格式设置
        JSONObject responseFormat = new JSONObject();
        responseFormat.put("type", "text");
        requestBody.put("response_format", responseFormat);

        // 其他参数设置
        requestBody.put("stop", null);
        requestBody.put("stream", true);
        requestBody.put("temperature", 1);
        requestBody.put("top_p", 1);
        requestBody.put("tools", null);
        requestBody.put("tool_choice", "none");
        requestBody.put("logprobs", false);
        requestBody.put("top_logprobs", null);

        // 创建HTTP连接
        URL url = new URL(baseUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);

        // 发送请求数据
        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
        }

        // 流式读取响应并实时推送到客户端
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String jsonStr = line.substring(6).trim();
                    if ("[DONE]".equals(jsonStr)) {
                        break; // 流结束标志
                    }

                    // 解析增量消息
                    JSONObject data = JSON.parseObject(jsonStr);
                    JSONArray choices = data.getJSONArray("choices");
                    if (choices != null && !choices.isEmpty()) {
                        JSONObject delta = choices.getJSONObject(0).getJSONObject("delta");
                        if (delta != null) {
                            String replyContent = delta.getString("content");
                            if (replyContent != null) {
                                // 实时发送到客户端
                                session.getBasicRemote().sendText(replyContent);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getBasicRemote().sendText("请求发生错误: " + e.getMessage());
        }
    }
}
