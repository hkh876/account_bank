package com.example.accountbank.custom;

import com.example.accountbank.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@ToString
public class CustomMember extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private MemberEntity member;

    public CustomMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomMember(MemberEntity member, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.member = member;
    }
}
