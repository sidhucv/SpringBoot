package springframework.service;

import springframework.commands.UnitOfMeasureCommand;
import springframework.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {


    public Set<UnitOfMeasureCommand> getUom();
}
