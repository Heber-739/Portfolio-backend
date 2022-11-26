package com.portfolio.Service;

import com.portfolio.Entity.Tag;
import com.portfolio.Repository.RTag;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class STag {

    @Autowired
    RTag rTag;

    public List<Tag> list() {
        return rTag.findAll();
    }

    public List<Tag> findAllByEducationsId(int id) {
        return rTag.findAllByEducationsId(id);
    }

    public Optional<Tag> findByName(String name) {
        return rTag.findByName(name);
    }

    public Tag findById(int id) {
        return rTag.findById(id).orElse(null);
    }

    public boolean existsById(int id) {
        return rTag.existsById(id);
    }

    public boolean existsByName(String name) {
        return rTag.existsByName(name);
    }

    public void save(Tag tag) {
        rTag.save(tag);
    }

    public void delete(int id) {
        rTag.deleteById(id);
    }

}
