package pipeline;

import java.util.List;

public class SequentialStrategy implements PipelineStrategy {

    @Override
    public List<Processor> ordenar(List<Processor> processors) {
        return processors;
    }
}