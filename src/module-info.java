module weather {
    requires transitive javafx.controls;
    requires java.logging;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.dataformat.xml;
    requires java.sql;
    exports weather;
}