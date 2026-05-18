package pipeline;

import java.util.List;

// interface do padrao Strategy
// define como os processadores serao ordenados no pipeline
public interface PipelineStrategy {
    List<Processor> ordenar(List<Processor> processors);
}