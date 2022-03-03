package com.example.agentweb_imitate;

/**
 * Author:zx on 2019/9/609:11
 */
public class HookManager {
    public static AgentWebZ hookAgentWeb(AgentWebZ agentWeb, AgentWebZ.AgentBuilder agentBuilder) {
        return agentWeb;
    }

    public static boolean permissionHook(String url, String[] permissions) {
        return true;
    }
}
