package com.ktn3.core_banking.org.entity;

import java.util.ArrayList;
import java.util.List;

import com.ktn3.core_banking.org.constant.OrgType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "org_unit")
@NoArgsConstructor
@AllArgsConstructor
public class OrgUnit {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // tự tăng theo sở gd

    private String name;

    @Enumerated(EnumType.STRING)
    private OrgType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private OrgUnit parent;

    @OneToMany(mappedBy = "parent")
    private List<OrgUnit> children = new ArrayList<>();
}
