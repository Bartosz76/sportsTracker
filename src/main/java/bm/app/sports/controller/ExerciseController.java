package bm.app.sports.controller;

import bm.app.sports.dto.ExerciseDto;
import bm.app.sports.repository.ExerciseRepository;
import bm.app.sports.service.ExerciseCreateService;
import bm.app.sports.service.ExerciseGetAll;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ExerciseController {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    private final ExerciseRepository exerciseRepository;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    private String exerciseName = "";
    private int seriesAmount;
    private final ExerciseCreateService exerciseCreateService;
    private final ExerciseGetAll exerciseGetAll;

    @GetMapping("menu")
    public String getMenu() {
        return "menu";
    }

    @PostMapping("menu")
    public String backToMenu(){
        return "menu";
    }

    @PostMapping("goHardOrGoHome")
    public String goToHome() {
        return "home";
    }

    @GetMapping("home")
    public String getHome() {
        return "home";
    }

    @PostMapping("regularPushUps")
    public String regularPushUpsExercise() {
        this.exerciseName = "Regular push ups";
        return "series";
    }

    @PostMapping("oneLegRaisedPushUps")
    public String oneLegRaisedPushUpsExercise() {
        this.exerciseName = "One leg raised push ups";
        return "series";
    }

    @PostMapping("declinePushUps")
    public String declinePushUpsExercise() {
        this.exerciseName = "Decline push ups";
        return "series";
    }

    @PostMapping("archerPushUps")
    public String archerPushUpsExercise() {
        this.exerciseName = "Archer push ups";
        return "series";
    }

    @PostMapping("narrowPushUps")
    public String narrowPushUpsExercise() {
        this.exerciseName = "Narrow push ups";
        return "series";
    }

    @PostMapping("diamondPushUps")
    public String diamondPushUpsExercise() {
        this.exerciseName = "Diamond push ups";
        return "series";
    }

    @PostMapping("barsPushUps")
    public String barsPushUpsExercise() {
        this.exerciseName = "Bars push ups";
        return "series";
    }

    @PostMapping("barPullUps")
    public String barPullUpsExercise() {
        this.exerciseName = "Bar pull ups";
        return "series";
    }

    @PostMapping("dumbbellForeamsSupported")
    public String dumbbellForeamsSupportedExercise() {
        this.exerciseName = "Dumbbell forearms supported";
        return "series";
    }

    @PostMapping("dumbbellForearmsStanding")
    public String dumbbellForearmsStandingExercise() {
        this.exerciseName = "Dumbbell forearms standing";
        return "series";
    }

    @PostMapping("handGrips")
    public String handGripsExercise() {
        this.exerciseName = "Hand grips";
        return "series";
    }

    @PostMapping("hoodDumbbellRaises")
    public String hoodDumbbellRaisesExercise() {
        this.exerciseName = "Hood dumbbell raises";
        return "series";
    }

    @PostMapping("bicepsCurls")
    public String bicepsCurlsExercise() {
        this.exerciseName = "Biceps curls";
        return "series";
    }

    @PostMapping("barsLegRaises")
    public String barsLegRaisesExercise() {
        this.exerciseName = "Bars leg raises";
        return "series";
    }

    @PostMapping("rollerRounds")
    public String rollerRoundsExercise() {
        this.exerciseName = "Roller rounds";
        return "series";
    }

    @PostMapping("shoulderDumbbellRaises")
    public String shoulderDumbbellRaisesExercise() {
        this.exerciseName = "Shoulder dumbbell raises";
        return "series";
    }

    @PostMapping("repetitions")
    public String numberOfSeries(@RequestParam int seriesAmount){
        Optional<Integer> optional = Optional.ofNullable(seriesAmount);
        this.seriesAmount = optional.get();
        if (seriesAmount == 0){
            return "series";
        }
        return "repetitions";
    }

    @PostMapping("allrecords")
    public String displayAllRecords(Model model) {
        List<ExerciseDto> listOfRecords = exerciseGetAll.listOfExercises(exerciseRepository.findAll());
        model.addAttribute("listOfRecords", listOfRecords);
        return "allrecords";
    }

    @PostMapping("morePushUpsPlease")
    public String displayAllPushups(Model model) {
        List<ExerciseDto> listOfPushups = exerciseGetAll.listOfExercises(exerciseRepository.findAllPushups());
        model.addAttribute("listOfPushups", listOfPushups);
        return "allpushups";
    }

    @PostMapping("finalResult")
    public String finalResultPage(@RequestParam Optional<Integer> repetitions,
                                                                  Model model){
        int extractedRepetitions = 0;
        if (repetitions.isPresent()){
            extractedRepetitions = repetitions.get();
        }else {
            return "repetitions";
        }
        int finalExtractedRepetitions = extractedRepetitions;
        if (IntStream
                .range(2, extractedRepetitions)
                .noneMatch(index -> finalExtractedRepetitions % index == 0)){
            return "repetitions";
        }

        Date date = new Date();
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setDay(date);
        exerciseDto.setExerciseType(this.exerciseName);
        exerciseDto.setSeries(this.seriesAmount);
        exerciseDto.setRepetitions(finalExtractedRepetitions);
        exerciseCreateService.createExercise(exerciseDto);
        model.addAttribute("date", date);
        model.addAttribute("exercise", this.exerciseName);
        model.addAttribute("series", this.seriesAmount);
        model.addAttribute("repetitions", finalExtractedRepetitions);
        return "finalResult";
    }
}
