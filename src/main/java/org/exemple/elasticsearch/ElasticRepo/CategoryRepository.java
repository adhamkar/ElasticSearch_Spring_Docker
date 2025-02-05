package org.exemple.elasticsearch.ElasticRepo;
import org.exemple.elasticsearch.entities.Category;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CategoryRepository extends ElasticsearchRepository<Category, String> {
    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"name\",\"type\"],\"fuzziness\": \"AUTO\"}}")
    List<Category> searchByNameAndType(String query);
    @Query("{" +
            "\"bool\": {" +
            "  \"should\": [" +
            "    {\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name\", \"type\"], \"fuzziness\": \"AUTO\"}}," +
            "    {\"nested\": {" +
            "      \"path\": \"products\"," +
            "      \"query\": {" +
            "        \"multi_match\": {" +
            "          \"query\": \"*?0*\"," +
            "          \"fields\": [\"products.name\",\"products.description\"]," +
            "          \"fuzziness\": \"AUTO\"" +
            "        }" +
            "      }" +
            "    }}" +
            "  ]" +
            "}}")
    List<Category> searchMultipleFields(String query);
    Category findCategoryById(String id);
    List<Category> findAll();

}
