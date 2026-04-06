package pipeline;

import model.DataFile;

// interface que todos os processadores precisam seguir
// garante que qualquer etapa do pipeline tenha o metodo process

public interface Processor {

    DataFile process(DataFile dataFile);

    String getName();
}
