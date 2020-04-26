package org.fit.linevich.mapper;

import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.IllnessEntity;
import org.fit.linevich.domain.ProviderEntity;
import org.fit.linevich.domain.ZooEntity;
import org.fit.linevich.model.AnimalType;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Development;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.model.Gender;
import org.fit.linevich.model.PhysState;
import org.fit.linevich.model.Season;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.Employee;
import org.fit.linevich.views.Feed;
import org.fit.linevich.views.Illness;
import org.fit.linevich.views.Provider;
import org.fit.linevich.views.Zoo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CustomDataMapper {
    @Mapping(ignore = true, target = "vaccinesById")
    @Mapping(ignore = true, target = "responsibleAnimalsById")
    @Mapping(ignore = true, target = "oddDayRationsById")
    @Mapping(ignore = true, target = "medCardById")
    @Mapping(ignore = true, target = "illnessAnimalsById")
    @Mapping(ignore = true, target = "evenDayRationsById")
    @Mapping(ignore = true, target = "cellsAnimalsById")
    @Mapping(ignore = true, target = "animalReceiptsById")
    @Mapping(ignore = true, target = "animalCompatibilitiesById")
    @Mapping(ignore = true, target = "accessAnimalsById")
    public abstract AnimalEntity toAnimalEntity(Animal animal);
    @Mapping(ignore = true, target = "accessAnimalsById")
    @Mapping(ignore = true, target = "responsibleAnimalsById")
    public abstract EmployeeEntity toEmployeeEntity(Employee employee);
    @Mapping(ignore = true, target = "evenDayRationsById")
    @Mapping(ignore = true, target = "oddDayRationsById")
    @Mapping(ignore = true, target = "providersSpecializationsById")
    @Mapping(ignore = true, target = "suppliesById")
    public abstract  FeedEntity toFeedEntity(Feed feed);
    @Mapping(ignore = true, target = "illnessAnimalsById")
    public abstract  IllnessEntity toIllnessEntity(Illness illness);
    @Mapping(ignore = true, target = "providersSpecializationsByProvId")
    @Mapping(ignore = true, target = "suppliesByProvId")
    public abstract  ProviderEntity toProviderEntity(Provider provider);
    @Mapping(ignore = true, target = "animalReceiptsById")
    public abstract  ZooEntity toZooEntity(Zoo zoo);

    public abstract  Animal toAnimalView(AnimalEntity animalEntity);
    public abstract  Employee toEmployeeView(EmployeeEntity employeeEntity);
    public abstract  Feed toFeedView(FeedEntity feedEntity);
    public abstract  Illness toIllnessView(IllnessEntity illnessEntity);
    public abstract  Provider toProviderView(ProviderEntity providerEntity);
    public abstract  Zoo toZooView(ZooEntity zooEntity);

    AnimalType toAnimalType(String name){
        return AnimalType.findByName(name);
    }
    ClimaticZone toClimaticZone(String name){
        return ClimaticZone.findByName(name);
    }
    Development toDevelopment(String name){
        return Development.findByName(name);
    }
    EmployeeCategory toEmployeeCategory(String name){
        return EmployeeCategory.findByName(name);
    }
    Gender toGender(String name){
        return Gender.findByName(name);
    }
    PhysState toPhysState(String name){
        return PhysState.findByName(name);
    }
    Season toSeason(String name){
        return Season.findByName(name);
    }
}
