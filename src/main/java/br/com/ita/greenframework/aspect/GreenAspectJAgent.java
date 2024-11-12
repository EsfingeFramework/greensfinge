package br.com.ita.greenframework.aspect;

import org.aspectj.weaver.loadtime.Agent;

import java.lang.instrument.Instrumentation;

public class GreenAspectJAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        Agent.premain(agentArgs, inst);
    }
}
