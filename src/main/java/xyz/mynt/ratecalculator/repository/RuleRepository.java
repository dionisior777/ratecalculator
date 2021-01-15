package xyz.mynt.ratecalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.mynt.ratecalculator.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {

}
