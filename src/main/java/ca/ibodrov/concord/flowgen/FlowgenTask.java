package ca.ibodrov.concord.flowgen;

import com.walmartlabs.concord.runtime.v2.model.*;
import com.walmartlabs.concord.runtime.v2.sdk.Compiler;
import com.walmartlabs.concord.runtime.v2.sdk.Execution;
import com.walmartlabs.concord.runtime.v2.sdk.Task;
import com.walmartlabs.concord.runtime.v2.sdk.TaskContext;
import com.walmartlabs.concord.svm.Command;
import com.walmartlabs.concord.svm.Frame;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named("flowgen")
public class FlowgenTask implements Task {

    @Override
    public Serializable execute(TaskContext ctx) throws Exception {
        Execution execution = ctx.execution();

        List<Step> steps = Arrays.asList(
                TaskCall.singleArgCall(null, TaskCallOptions.builder().build(), "log", "Hello!"),
                TaskCall.singleArgCall(null, TaskCallOptions.builder().build(), "log", "Bye!"));

        Compiler compiler = ctx.compiler();
        ProcessDefinition pd = execution.processDefinition();
        Command cmd = compiler.compile(pd, new GroupOfSteps(null, steps, GroupOptions.builder().build()));

        Frame frame = new Frame(cmd);
        execution.state().pushFrame(execution.currentThreadId(), frame);

        return null;
    }
}
