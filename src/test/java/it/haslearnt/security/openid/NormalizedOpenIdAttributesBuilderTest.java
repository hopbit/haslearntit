package it.haslearnt.security.openid;

import org.fest.util.Collections;
import org.junit.Test;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NormalizedOpenIdAttributesBuilderTest {
    private static final String email = "some@email.pl";
    private static final String emailAttributeKey = "email";
    private static final String identityUrl = "myId";
    private static final String firstName = "Johnny";
    private static final String firstNameAttributeKey = "fname";
    private static final String lastName = "Rambo";
    private static final String lastNameAttributeKey = "lname";
    private static final String fullNameAttributeKey = "fullName";
    private static final String fullName = firstName + " " + lastName;


    @Test
    public void shouldBuildWithAttributesFromGoogle() {
        //given
        NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder = createBuilder();
        OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(OpenIDAuthenticationStatus.SUCCESS, identityUrl, "some message", createGoogleAttributes());

        //when
        NormalizedOpenIdAttributes normalizedOpenIdAttributes = normalizedOpenIdAttributesBuilder.build(token);

        //then
        assertEquals(email, normalizedOpenIdAttributes.getEmailAddress());
        assertEquals(identityUrl, normalizedOpenIdAttributes.getUserLocalIdentifier());
        assertEquals(fullName, normalizedOpenIdAttributes.getFullName());
    }


    private List<OpenIDAttribute> createGoogleAttributes() {
        List<OpenIDAttribute> openIDAttributes = new ArrayList<OpenIDAttribute>();
        openIDAttributes.add(new OpenIDAttribute(emailAttributeKey, "String", Collections.list(email)));
        openIDAttributes.add(new OpenIDAttribute(firstNameAttributeKey, "String", Collections.list(firstName)));
        openIDAttributes.add(new OpenIDAttribute(lastNameAttributeKey, "String", Collections.list(lastName)));
        return openIDAttributes;
    }

    @Test
    public void shouldBuildWithAttributesFromOpenId() {
        //given
        NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder = createBuilder();
        OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(OpenIDAuthenticationStatus.SUCCESS, identityUrl, "some message", createOpenIdAttributes());

        //when
        NormalizedOpenIdAttributes normalizedOpenIdAttributes = normalizedOpenIdAttributesBuilder.build(token);

        //then
        assertEquals(email, normalizedOpenIdAttributes.getEmailAddress());
        assertEquals(identityUrl, normalizedOpenIdAttributes.getUserLocalIdentifier());
        assertEquals(fullName, normalizedOpenIdAttributes.getFullName());
    }


    private List<OpenIDAttribute> createOpenIdAttributes() {
        List<OpenIDAttribute> openIDAttributes = new ArrayList<OpenIDAttribute>();
        openIDAttributes.add(new OpenIDAttribute(emailAttributeKey, "String", Collections.list(email)));
        openIDAttributes.add(new OpenIDAttribute(fullNameAttributeKey, "String", Collections.list(fullName)));
        return openIDAttributes;
    }

    private NormalizedOpenIdAttributesBuilder createBuilder() {
        NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder = new NormalizedOpenIdAttributesBuilder();
        normalizedOpenIdAttributesBuilder.setEmailAddressAttributeNames(new HashSet<String>(Collections.list(emailAttributeKey)));
        normalizedOpenIdAttributesBuilder.setFirstNameAttributeNames(new HashSet<String>(Collections.list(firstNameAttributeKey)));
        normalizedOpenIdAttributesBuilder.setLastNameAttributeNames(new HashSet<String>(Collections.list(lastNameAttributeKey)));
        normalizedOpenIdAttributesBuilder.setFullNameAttributeNames(new HashSet<String>(Collections.list(fullNameAttributeKey)));
        return normalizedOpenIdAttributesBuilder;
    }
}
