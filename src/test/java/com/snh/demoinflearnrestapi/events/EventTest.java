package com.snh.demoinflearnrestapi.events;

import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
class EventTest {


    @Test
    public void builder(){
        Event event = Event.builder().build();

        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){

        //given
        String name = "Spring";
        String description = "REST API";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);

    }

    @ParameterizedTest
    @MethodSource("parametersForTestFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree){

        //given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isFree()).isEqualTo(isFree);

    }

    private static Stream<Arguments> parametersForTestFree() {
        return Stream.of(
                Arguments.of(0, 0, true),
                Arguments.of(0, 100, false),
                Arguments.of(100, 0, false));
    }


    @ParameterizedTest
    @MethodSource("parametersForOfflineTest")
    public void offlineTest(String location, boolean isOffline) {
        //given
        Event event = Event.builder()
                .location(location)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    private static Stream<Arguments> parametersForOfflineTest() {
        return Stream.of(
                Arguments.of("강남", true),
                Arguments.of(" ", false),
                Arguments.of(null, false)
        );
    }
}