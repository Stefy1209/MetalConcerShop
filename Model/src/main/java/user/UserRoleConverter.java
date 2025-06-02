package user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserRole userRole) {
        return (userRole == null) ? null : userRole.getCode();
    }

    @Override
    public UserRole convertToEntityAttribute(Integer integer) {
        return (integer == null) ? null : UserRole.fromCode(integer);
    }
}
