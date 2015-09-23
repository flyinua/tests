/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x1.markdown.repository;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

/**
 *
 * @author vlad
 */
public class MarkdownDocument {

    @Id
    private String id;

    @CreatedDate
    private DateTime createdAt;

    @CreatedBy
    private String createdBy;

    private String data;

    public MarkdownDocument(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("MarkdownDocument[id=%s, createdBy='%s', createdAt='%s', data='%s']", id, createdBy, createdAt, data);
    }
}
