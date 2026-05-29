package pipeline;

import java.util.List;

// define como os processadores serao ordenados no pipeline
public interface PipelineStrategy {
    List<Processor> ordenar(List<Processor> processors);
}