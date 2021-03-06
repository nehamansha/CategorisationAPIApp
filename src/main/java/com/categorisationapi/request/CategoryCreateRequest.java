package com.categorisationapi.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * CategoryCreateRequest
 * (Class generated by swagger)
 */
public class CategoryCreateRequest {

    @JsonProperty(value="category", required=true)
    private String category;

    public CategoryCreateRequest category(String category) {
        this.category = category;
        return this;
    }

    /**
     * Get category
     * @return category
     **/
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryCreateRequest categoryCreateRequest = (CategoryCreateRequest) o;
        return Objects.equals(this.category, categoryCreateRequest.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CategoryCreateRequest {\n");

        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

