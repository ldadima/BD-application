package org.fit.linevich.mapper;

import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.IllnessEntity;
import org.fit.linevich.domain.ProviderEntity;
import org.fit.linevich.domain.ZooEntity;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.Employee;
import org.fit.linevich.views.Feed;
import org.fit.linevich.views.Illness;
import org.fit.linevich.views.Provider;
import org.fit.linevich.views.Zoo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
    public abstract void toAnimalEntity(Animal animal, @MappingTarget AnimalEntity animalEntity);

    @Mapping(ignore = true, target = "accessAnimalsById")
    @Mapping(ignore = true, target = "responsibleAnimalsById")
    public abstract void toEmployeeEntity(Employee employee, @MappingTarget EmployeeEntity employeeEntity);

    @Mapping(ignore = true, target = "evenDayRationsById")
    @Mapping(ignore = true, target = "oddDayRationsById")
    @Mapping(ignore = true, target = "providersSpecializationsById")
    @Mapping(ignore = true, target = "suppliesById")
    public abstract void toFeedEntity(Feed feed, @MappingTarget FeedEntity feedEntity);

    @Mapping(ignore = true, target = "illnessAnimalsById")
    public abstract void toIllnessEntity(Illness illness, @MappingTarget IllnessEntity illnessEntity);

    @Mapping(ignore = true, target = "providersSpecializationsByProvId")
    @Mapping(ignore = true, target = "suppliesByProvId")
    public abstract void toProviderEntity(Provider provider, @MappingTarget ProviderEntity providerEntity);

    @Mapping(ignore = true, target = "animalReceiptsById")
    public abstract void toZooEntity(Zoo zoo, @MappingTarget ZooEntity zooEntity);

    public abstract Animal toAnimalView(AnimalEntity animalEntity);

    public abstract List<Animal> toAnimalListView(List<AnimalEntity> animalEntity);

    public abstract Employee toEmployeeView(EmployeeEntity employeeEntity);

    public abstract List<Employee> toEmployeeListView(List<EmployeeEntity> employeeEntity);

    public abstract Feed toFeedView(FeedEntity feedEntity);

    public abstract List<Feed> toFeedListView(Iterable<FeedEntity> feedEntity);

    public abstract Illness toIllnessView(IllnessEntity illnessEntity);
    public abstract List<Illness> toIllnessListView(Iterable<IllnessEntity> illnessEntity);

    public abstract Provider toProviderView(ProviderEntity providerEntity);
    public abstract List<Provider> toProviderListView(Iterable<ProviderEntity> providerEntity);

    public abstract Zoo toZooView(ZooEntity zooEntity);

    public abstract List<Zoo> toZooListView(Iterable<ZooEntity> zooEntity);

    // AnimalType toAnimalType(String name) {
    //     return AnimalType.findByName(name);
    // }
    //
    // ClimaticZone toClimaticZone(String name) {
    //     return ClimaticZone.findByName(name);
    // }
    //
    // Development toDevelopment(String name) {
    //     return Development.findByName(name);
    // }
    //
    // EmployeeCategory toEmployeeCategory(String name) {
    //     return EmployeeCategory.findByName(name);
    // }
    //
    // Gender toGender(String name) {
    //     return Gender.findByName(name);
    // }
    //
    // PhysState toPhysState(String name) {
    //     return PhysState.findByName(name);
    // }
    //
    // Season toSeason(String name) {
    //     return Season.findByName(name);
    // }
}
