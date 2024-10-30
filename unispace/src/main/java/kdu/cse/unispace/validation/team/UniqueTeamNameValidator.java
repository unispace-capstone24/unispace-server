package kdu.cse.unispace.validation.team;

import kdu.cse.unispace.domain.Team;
import kdu.cse.unispace.repository.team.TeamRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueTeamNameValidator implements ConstraintValidator<UniqueTeamName, Object> {

    private final TeamRepository teamRepository;
    @Override
    public void initialize(UniqueTeamName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String teamName = (String) value;
        Optional<Team> team = teamRepository.findByTeamName(teamName);
        return team.isEmpty();
    }
}
