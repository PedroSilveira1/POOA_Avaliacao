package pipeline;

import model.DataFile;

// interface que todos os processadores precisam seguir

public interface Processor {

    DataFile process(DataFile dataFile);

    String getName();
}
