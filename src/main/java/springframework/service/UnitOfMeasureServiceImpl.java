package springframework.service;

import org.springframework.stereotype.Service;
import springframework.commands.UnitOfMeasureCommand;
import springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import springframework.domain.Recipe;
import springframework.domain.UnitOfMeasure;
import springframework.repositories.UnitofMeasureRepository;

import java.util.HashMap;
import java.util.HashSet;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    UnitofMeasureRepository unitofMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitofMeasureRepository unitofMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitofMeasureRepository = unitofMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    public Set<UnitOfMeasureCommand> getUom(){
        Set<UnitOfMeasureCommand> test = new HashSet<>();
        return StreamSupport.stream(unitofMeasureRepository.findAll().spliterator(),false).
                map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
    }
}
