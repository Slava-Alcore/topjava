package ru.javawebinar.topjava.service.usertests;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJPAUserServiceTest extends AbstractUserTest {

}
