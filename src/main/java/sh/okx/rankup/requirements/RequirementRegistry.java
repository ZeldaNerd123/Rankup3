package sh.okx.rankup.requirements;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RequirementRegistry {
  private final Set<Requirement> requirements = new HashSet<>();

  public void addRequirement(Requirement requirement) {
    requirements.add(requirement);
  }

  public void addRequirements(Requirement... requirements) {
    Collections.addAll(this.requirements, requirements);
  }

  public Requirement newRequirement(String name, String value) {
    for (Requirement requirement : requirements) {
      if (requirement.getName().equalsIgnoreCase(name)) {
        Requirement newRequirement = requirement.clone();
        newRequirement.setValue(value);
        return newRequirement;
      }
    }
    return null;
  }

  public Set<Requirement> getRequirements(Iterable<String> list) {
    Set<Requirement> requirements = new HashSet<>();

    for (String req : list) {
      String[] parts = req.split(" ", 2);
      if (parts.length < 2) {
        throw new IllegalArgumentException("For requirement: '" + req + "'. Requirements must contain a space between" +
            " the name of the requirement and the value of the requirement. If it already looks like it has a space, " +
            "make sure it is not a tab or has an invisible character.");
      }

      String name = parts[0];
      String value = parts[1];
      Requirement requirement = newRequirement(name, value);
      Objects.requireNonNull(requirement, name.equalsIgnoreCase("money") ? "The 'money' requirement is being used but no economy is found" : "Unknown requirement: " + name);
      requirements.add(requirement);
    }
    return requirements;
  }
}
