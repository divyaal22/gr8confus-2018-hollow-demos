package io.perezalcolea.hollow.plain;

import com.netflix.hollow.api.codegen.HollowAPIGenerator;

public class ApiGenerator {

    public static void main(String[] args) {
        try {
            HollowAPIGenerator generator = new HollowAPIGenerator.Builder().withAPIClassname("BooksAPI")
                    .withPackageName("io.perezalcolea.hollow.plain.api")
                    .withDataModel(Book.class)
                    .withDestination("./data-model/src/main/java")
                    .build();

            generator.generateSourceFiles();
        } catch (Exception e) {
            System.out.println("Could not generate API files " + e);
        }
    }
}
