package com.w4springrain.datajpa.repository;

import com.w4springrain.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
