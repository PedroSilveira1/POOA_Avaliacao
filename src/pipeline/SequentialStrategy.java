package pipeline;

import java.util.List;

// estrategia padrao: executa na ordem em que foram adicionados
public class SequentialStrategy implements PipelineStrategy {

    @Override
    public List<Processor> ordenar(List<Processor> processors) {
        return processors;
    }
}