package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// annotation personalizada pra marcar os processadores
// o campo ordem define a sequencia de execucao no pipeline
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Processo {
    int ordem();
}