package org.infinispan.tutorial.data;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.Proto;

/**
 * Annotated with Protostream
 */
@Indexed
@Proto
public record LocationWeather(@Basic
                              float temperature,
                              @Basic
                              String condition,
                              @Keyword(projectable = true, sortable = true, normalizer = "lowercase", indexNullAs = "unnamed", norms = false)
                              String city,
                              @Keyword(projectable = true, sortable = true, normalizer = "lowercase", indexNullAs = "unnamed", norms = false)
                              String country) {
}
