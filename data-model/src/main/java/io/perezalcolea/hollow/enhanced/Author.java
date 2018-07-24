package io.perezalcolea.hollow.enhanced;

import com.netflix.hollow.core.write.objectmapper.HollowInline;

public class Author {
    long id;
    @HollowInline
    String authorName;
}
