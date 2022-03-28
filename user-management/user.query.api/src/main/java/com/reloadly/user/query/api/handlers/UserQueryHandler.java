package com.reloadly.user.query.api.handlers;

import com.reloadly.user.query.api.dto.UserLookupResponse;
import com.reloadly.user.query.api.queries.FindAllUsersQuery;
import com.reloadly.user.query.api.queries.FindUserByIdQuery;
import com.reloadly.user.query.api.queries.SearchUserQuery;

public interface UserQueryHandler {

    UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery);
    UserLookupResponse searchUsers(SearchUserQuery searchUserQuery);
    UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery);
}
