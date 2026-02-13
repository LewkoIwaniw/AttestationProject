package ua.inf.iwanoff.attestation.view;

import ua.inf.iwanoff.utils.MultiString;

public class Strings {
    public static final String VERSION = "2.01";

    public static final MultiString
            msWrsAttestation = new MultiString("WRS Attestation", "Атестація РСЗ", "Аттестация РСО"),
            msMFile = new MultiString("_File", "_Файл", "_Файл"),
            msMNew = new MultiString("_New", "_Новий", "_Новый"),
            msMOpen = new MultiString("_Open...", "_Відкрити...", "_Открыть..."),
            msMImport = new MultiString("_Import TXT...", "_Імпортувати TXT...", "_Импортировать TXT..."),
            msMSave = new MultiString("_Save", "_Зберегти", "_Сохранить"),
            msMSaveAs = new MultiString("Save _As...", "Зберегти _Як...", "Сохранить _Как..."),
            msMExport = new MultiString("_Export TXT...", "_Експортувати TXT...", "_Экспортировать TXT..."),
            msMReport = new MultiString("Save _Report...", "З_берегти звіт...", "Сохранить _отчет..."),
            msMPrint = new MultiString("_Print", "_Друк", "_Печать"),
            msMExit = new MultiString("E_xit", "Вий_ти", "Вый_ти"),
            msMEdit = new MultiString("_Edit", "Р_едагування", "_Правка"),
            msMCut = new MultiString("C_ut", "_Вирізати", "_Вырезать"),
            msMCopy = new MultiString("_Copy", "_Скопіювати", "_Скопировать"),
            msMPaste = new MultiString("_Paste", "В_ставити", "В_ставить"),
            msMRun = new MultiString("_Run", "_Робота", "_Работа"),
            msMOptions = new MultiString("_Options", "_Опції", "_Опции"),
            msMLanguage = new MultiString("_Language", "_Language / Мова", "_Язык / Language"),
            msMPreferences = new MultiString("_Preferences", "_Налаштування", "Н_астройки"),
            msMHelp = new MultiString("_Help", "_Довідка", "_Справка"),
            msMAbout = new MultiString("_About...", "_Про програму...", "_ О программе..."),
            msSaveReport = new MultiString("Save Report", "Зберегти звіт", "Сохранить отчет"),
            msOpen = new MultiString("Open File", "Відкрити файл", "Открыть файл"),
            msOpenXMLFile = new MultiString("Open XML File", "Відкрити XML-файл", "Открыть XML-файл"),
            msImportTxt = new MultiString("Import from TXT File",
                    "Імпортувати з TXT-файлу",
                    "Импортировать из TXT-файла"),
            msSaveXMLFile = new MultiString("Save XML File", "Зберегти XML-файл", "Сохранить XML-файл"),
            msFilterXML = new MultiString("XML files", "XML-файли", "XML-файлы"),
            msFilterTXT = new MultiString("TXT files", "файли TXT", "файлы TXT"),
            msFilterPDF = new MultiString("PDF files", "файли PDF", "файлы PDF"),
            msFilter = new MultiString(" files", "-файли", "-файлы"),
            msFilterAll = new MultiString("All files", "Усі файли", "Все файлы"),
            msFileNotFound = new MultiString("File not found", "Файл не знайдено", "Файл не найден"),
            msFileReadError = new MultiString("File read error", "Помилка читання з файлу", "Ошибка чтения из файла"),
            msWrongFormat = new MultiString("Wrong file format", "Хибний формат файлу", "Неправильный формат файла"),
            msSampleTitle = new MultiString("Sample Title", "Назва вибірки", "Наименование выборки"),
            msFlag = new MultiString("Flag (SS/WRS)", "Флаг (СЗ/РСЗ)", "Флаг (СО/РСО)"),
            msConcentration = new MultiString("Concentration", "Концентрація", "Концентрация"),
            msUncertainty = new MultiString(
                    "Uncertainty of sample preparation",
                    "Невизначеність пробопідготовки",
                    "Неопределенность пробоподготовки"),
            msNRSO = new MultiString("WRS Title", "Назва РСЗ", "Название РСО"),
            msShema = new MultiString("Pattern ", "Схема ", "Схема "),
            msUAZRSO = new MultiString(
                    "Ascertainment of WRS Certified Value", // ??????????????????????????????
                    "Встановлення атестованого значення РСЗ",
                    "Установление аттестованного значения РСО"),
            msUAZPORSO = new MultiString(
                    "Ascertainment of WRS Certified Value and Verification of WRS Homogeneity",
                    "Встановлення атестованого значення і перевірка однорідності РСЗ",
                    "Установление аттестованного значения и проверка однородности РСО"),
            msPORSO = new MultiString(
                    "Verification of WRS Homogeneity",
                    "Перевірка однорідності РСЗ",
                    "Проверка однородности РСО"),
            msWRS = new MultiString("WRS", "РСЗ", "РСО"),
            //msFSS = new MultiString("FRS", "ФСЗ", "ФСО"),
            msFSS = new MultiString("RS", "СЗ", "СО"),
            msNumber = new MultiString("#", "№", "№"),
            msProtocolNumber = new MultiString("Protocol number", "Номер протоколу", "Номер протокола"),
            msPIUAZRSO = new MultiString(
                    "Test protocol \"WRS Certified Value\"", // ??????????????????????????????
                    "Протокол випробування \"Встановлення атестованого значення РСЗ\"",
                    "Протокол испытания \"Установление аттестованного значения РСО\""),
            msPIUAZPORSO = new MultiString(
                    "Test protocol \"WRS Certified Value and Verification of WRS Homogeneity\"",
                    "Протокол випробування \"Встановлення атестованого значення і перевірка однорідності РСЗ\"",
                    "Протокол испытания \"Установление аттестованного значения и проверка однородности РСО\""),
            msPIPORSO = new MultiString(
                    "Test protocol \"Verification of WRS Homogeneity\"",
                    "Протокол випробування \"Перевірка однорідності РСЗ\"",
                    "Протокол испытания \"Проверка однородности РСО\""),
            msNomer = new MultiString("Protocol number", "Номер протоколу", "Номер протокола"),
            msPPAZSO = new MultiString(
                    "Working standard sample certified values assignment protocol",
                    "Протокол присвоєння атестованого значення робочого стандартного зразка",
                    "Протокол присвоения аттестованного значения рабочего стандартного образца"),
            msStuff = new MultiString(
                    "Stuff for attestation (producer / supplier, series)",
                    "Матеріал для атестації (виробник/постачальник, серія)",
                    "Материал для аттестации (производитель/поставщик, серия)"),
            msAmountOfSubstance = new MultiString(
                    "The amount of substance taken for attestation",
                    "Кількість речовини, узята для атестації",
                    "Количество вещества, взятое для аттестации"),
            msOfficialReferenceSample = new MultiString("Official RS", "Офіційний СЗ", "Официальный СО"),
            msWrsDocument = new MultiString(
                    "The document, according to which tests are carried out",
                    "Документ, відповідно до якого проведені випробування",
                    "Документ, в соответствии с которым проведены испытания"),
            msTestDate = new MultiString("Test dateSorted", "Дата проведення випробувань", "Дата проведения испытаний"),
            msED = new MultiString("Experimental data", "Експериментальні дані", "Экспериментальные данные"),
            msID = new MultiString("Source data", "Вихідні дані", "Исходные данные"),
            msNO = new MultiString("Weights, Dilutions", "Наважки, розведення", "Навески, разведения"),
            msRastvor = new MultiString("Solution", "Розчин", "Раствор"),
            msAS = new MultiString("Analytical signals", "Аналітичні сигнали", "Аналитические сигналы"),
            msNIS = new MultiString("No of measurement", "№ вимірювання", "№ измерения"),
            msRRS = new MultiString("Results of calculations", "Результати розрахунків", "Результаты расчетов"),
            msKRS = new MultiString(
                    "Concentration of the solution", "Концентрація розчину", "Концентрация раствора"),
            msSZASJRS = new MultiString(
                    "Average value of analytical signal for the j-order solution",
                    "Середнє значення аналітичного сигналу для j-того розчину",
                    "Среднее значение аналитического сигнала для j-того раствора"),
            msOSOASJRS = new MultiString(
                    "Relative standard deviation of analytical signal for the j-order solution",
                    "Відносне стандартне відхилення аналітичного сигналу для j-того розчину",
                    "Относительное стандартное отклонение аналитического сигнала для j-того раствора"),
            msNZJRS = new MultiString(
                    "Normalized value for the j-order solution",
                    "Нормалізоване значення для j-того розчину",
                    "Нормализованное значение для j-того раствора"),
            msNSZSOS = new MultiString(
                    "Normalized average of RS",
                    "Нормалізоване середнє значення СЗ",
                    "Нормализованное среднее значение СО"),
            msNSZRSOS = new MultiString(
                    "Normalized average of WRS",
                    "Нормалізоване середнє значення РСЗ",
                    "Нормализованное среднее значение РСО"),
            msCertifiedValueOfRS = new MultiString(
                    "Certified value of RS, %", "Атестоване значення СЗ, %", "Аттестованное значение СО, %"),
            msAZRSOS = new MultiString(
                    "Certified value of WRS, %", "Атестоване значення РСЗ, %", "Аттестованное значение РСО, %"),
            msSKKPR = new MultiString(
                    "Statistical quality control of the results",
                    "Статистичний контроль якості результатів",
                    "Статистический контроль качества результатов"),
            msPRD = new MultiString(
                    "Verification of variances equality ",
                    "Перевірка рівноточності дисперсій",
                    "Проверка равноточности дисперсий"),
            msKCRIT = new MultiString("Check criteria", "Контрольний критерій", "Контрольный критерий"),
            msRKB = new MultiString(
                    "Calculated Bartlett criteria",
                    "Розрахований критерій Бартлетта",
                    "Рассчитанный критерий Бартлетта"),
            msKBNR = new MultiString(
                    "Bartlett criteria can't be calculated for the single sample",
                    "Критерій Бартлетта для однієї виборки не розраховується",
                    "Критерий Бартлетта для одной выборки не рассчитывается"),
            msKB2NR = new MultiString(
                    "Bartlett criteria can't be calculated for the two sample",
                    "Критерій Бартлетта для двух виборок не розраховується",
                    "Критерий Бартлетта для двух выборок не рассчитывается"),
            msPRDDOVNR = new MultiString(
                    "Verification of the variances equality by Bartlett isn't preformed for two variances",
                    //???????????????????????????
                    "Перевірка равноточноcті дисперсій за Бартлеттом для двух вибірок не здійснюється",
                    "Проверка равноточности дисперсий по Бартлетту для двух выборок не осуществляется"),
            msTZKB = new MultiString(
                    "Tabular value of Bartlett criterion (P = 95%)",
                    "Табличне значення критерію Бартлетта (P = 95%)",
                    "Табличное значение критерия Бартлетта (P = 95%)"),
            msConclusion = new MultiString("Conclusion:", "Висновок:", "Заключение:"),
            msDRNVR = new MultiString(
                    "Variances differ non significantly",
                    "Дисперсії різняться незначуще, вибірки рівноточні",
                    "Дисперсии различаются незначимо, выборки равноточны"),
            msPODNMBV = new MultiString(
                    "Verification of the variances equality by Bartlett cannot be preformed "
                            + "for solutions with equal analytical signals",
                    "Перевірка рівноточності дисперсій не може бути виконана для розчинів з "
                            + "однаковими аналітичними сигналами",
                    "Проверка равноточности дисперсий не может быть выполнена для растворов "
                            + "с одинаковыми аналитическими сигналами"),
            msDRZVN = new MultiString(
                    "Variances differ significantly",
                    "Дисперсії різняться значуще, вибірки нерівноточні",
                    "Дисперсии различаются значимо, выборки неравноточны"),
            msPOV = new MultiString(
                    "Verification of the homogeneity of samples",
                    "Перевірка однорідності вибірок",
                    "Проверка однородности выборок"),
            msOtklonenija = new MultiString(
                    "Deviation  from the mean (%)",
                    "Відхилення від середнього значення (%):",
                    "Отклонения от среднего значения (%):"),
            msOCSS = new MultiString(
                    "Pooled number of degrees of freedom",
                    "Об'єднане число ступенів свободи",
                    "Объединенное число степеней свободы"),
            msOKS = new MultiString(
                    "One-sided Student's t test",
                    "Однобічний критерій Стьюдента",
                    "Односторонний критерий Стьюдента"),
            msOOSO = new MultiString(
                    "Pooled relative standard deviation",
                    "Об'єднане відносне стандартне відхилення",
                    "Объединенное относительное стандартное отклонение"),
            msMDO = new MultiString(
                    "Maximum allowable deviation, %",
                    "Максимально допустиме відхилення, %",
                    "Максимально допустимое отклонение, %"),
            msO = new MultiString("Outilers", "Однорідність", "Однородность"),
            msVO = new MultiString("no outliers", "однорідна", "однородна"),
            msVN = new MultiString("outlier is detected", "неоднорідна", "неоднородна"),
            msONPKZVO = new MultiString(
                    "Deviations do not exceed a critical value, no outliers",
                    "Відхилення не перевищують критичного значення, вибірки однорідні",
                    "Отклонения не превышают критического значения, выборки однородны"),
            msOPKZVN = new MultiString(
                    "Deviations exceed a critical value, outlier is detected",
                    "Відхилення перевищують критичне значення, вибірки неоднорідні",
                    "Отклонения превышают критическое значение, выборки неоднородны"),
            msPRNSZ = new MultiString(
                    "Verification of the differences of normalized average",
                    "Перевірка відмінностей нормалізованих середніх значень",
                    "Проверка различий нормализованных средних значений"),
            msNP = new MultiString(
                    "Uncertainty of sample preparation",
                    "Невизначеність пробопідготовки",
                    "Неопределенность пробоподготовки"),
            msMDRNSZ = new MultiString(
                    "Maximum acceptable difference of normalized average",
                    "Максимально допустима різниця нормалізованих середніх значень",
                    "Максимально допустимое различие нормализованных средних значений"),
            msRazliczie = new MultiString("The difference:", "Відмінність:", "Различие:"),
            msSNR = new MultiString(
                    "Statistical insignificance of differences:",
                    "Статистична незначимість відмінностей:",
                    "Статистическая незначимость различий:"),
            msPNR = new MultiString(
                    "Practical insignificance of differences:",
                    "Практична незначимість відмінностей:",
                    "Практическая незначимость различий:"),
            msNeznzczimo = new MultiString("are not significantly differ", "незначуще", "незначимо"),
            msZnaczimo = new MultiString("are significantly differ", "значуще", "значимо"),
            msNSZSOO = new MultiString(
                    "Normalized means of RS values differ",
                    "Нормалізовані середні значення СЗ відрізняються",
                    "Нормализованные средние значения СО отличаются"),
            msNSZRSOO = new MultiString(
                    "Normalized means of WRS values differ",
                    "Нормалізовані середні значення РСЗ відрізняються",
                    "Нормализованные средние значения РСО отличаются"),
            msPOD = new MultiString(
                    "Checking drift absence", "Перевірка відсутності дрейфу", "Проверка отсутствия дрейфа"),
            msOKI = new MultiString(
                    "The total number of measurements",
                    "Загальна кількість вимірювань",
                    "Общее количество измерений"),
            msName = new MultiString("Name", "Назва", "Название"),
            msBegin = new MultiString("beg.", "поч.", "нач."),
            msEnd = new MultiString("end.", "кін.", "кон."),
            msDifference = new MultiString("The difference (%)", "Різниця (%)", "Разность (%)"),
            msAverage = new MultiString("average", "середнє", "среднее"),
            msPD = new MultiString("The presence of drift", "Присутність дрейфу", "Присутствие дрейфа"),
            msNotSignificant = new MultiString("non significant", "незначущий", "незначим"),
            msSignificant = new MultiString("significant", "значущий", "значим"),
            msDriftIsStatisticallyNonSignificantForIndividualSamples = new MultiString(
                    "Drift is statistically non significant for individual samples",
                    "Дрейф для окремих вибірок статистично незначущий",
                    "Дрейф для отдельных выборок статистически незначим"),
            msDSZD = new MultiString(
                    "Drift is statistically significant for samples",
                    "Дрейф статистично значущий для вибірок",
                    "Дрейф статистически значим для выборок"),
            msDriftIsStatisticallySignificant = new MultiString(
                    "Drift is statistically significant",
                    "Дрейф статистично значущий",
                    "Дрейф статистически значим"),
            msSample = new MultiString("sample", "вибірки", "выборки"),
            msTime = new MultiString("time", "час", "время"),
            msSamples = new MultiString("Samples", "Вибірки", "Выборки"),
            msOperator = new MultiString("Operator", "Оператор", "Оператор"),
            msCertifiedValueOfWRS = new MultiString(
                    "Certified value of WRS", "Атестоване значення РСЗ", "Аттестованное значение РСО"),
            msUncertaintyOfCertifiedValueOfWRS = new MultiString(
                    "Uncertainty of certified value of WRS",
                    "Невизначеність атестованого значення РСЗ",
                    "Неопределенность аттестованного значения РСО"),
            msDeltaWRS = new MultiString(
                    "Requirements for the uncertainties of certified values of WRS",
                    "Вимоги до невизначеності атестованого значення РСЗ",
                    "Требования к неопределенности аттестованного значения РСО"),
            msNotCalculated = new MultiString("not calculated", "не розраховується", "не рассчитывается"),
            msSatisfied = new MultiString("are satisfied", "витримуються", "выдерживаются"),
            msNotSatisfied = new MultiString("are not satisfied", "не витримуються", "не выдерживаются"),
            msConclusions = new MultiString("Conclusions", "Висновки", "Выводы"),
            msAccordingToTestResultsForWRS = new MultiString(
                    "According to test results for WRS",
                    "За результатами випробувань для РСЗ",
                    "По результатам испытаний для РСО"),
            msEstablishedCertifiedValue = new MultiString(
                    "Established certified value:",
                    "встановлено атестоване значення:",
                    "установлено аттестованное значение: "),
            msContent = new MultiString("Content", "Вміст", "Содержание"),
            msAcceptabilityCriteriaForTheQualityOfTheResults = new MultiString(
                    "Acceptability criteria for the quality of the results",
                    "Критерії прийнятності для якості отриманих результатів",
                    "Критерии приемлемости для качества полученных результатов"),
            msAppointmentName = new MultiString("Appointment, name", "Посада, ПІБ", "Должность, ФИО"),
            msSignature = new MultiString("Signature", "Підпис", "Подпись"),
            msReportWasPreparedBy = new MultiString("Report was prepared by", "Протокол склав:", "Протокол составил:"),
            msReportWasCheckedBy = new MultiString(
                    "Report was checked by", "Протокол перевірив:", "Протокол проверил:"),
            msCheckingTheHomogeneityOfWRS = new MultiString(
                    "Checking the homogeneity of WRS", "Перевірка однорідності РСЗ", "Проверка однородности РСО"),
            msTwoSidedStudent_sTTest = new MultiString(
                    "Two-sided Student's t test", "Двобічний критерій Стьюдента", "Двусторонний критерий Стьюдента"),
            msTheRelativeStandardDeviationForASinglePackage = new MultiString(
                    "The relative standard deviation for a single package",
                    "Відносне стандартне відхилення для одиничного вмісту упаковки",
                    "Относительное стандартное отклонение для единичного содержания упаковки"),
            msOdiEsy = new MultiString(
                    "The relative confidence interval for a single content package",
                    "Відносний довірчий інтервал для одиничного вмісту упаковки",
                    "Относительный доверительный интервал для единичного содержания упаковки"),
            msOd = new MultiString(
                    "Requirements for the relative confidence interval for a single content package",
                    "Вимоги до відносного довірчого інтервалу для одиничного вмісту упаковки",
                    "Требования к относительному доверительному интервалу для единичного содержания упаковки"),
            msTodiesuvOp = new MultiString(
                    "Requirements for the relative confidence interval of the unit content of the " +
                            "package are satisfied, the homogeneity of WRS confirmed",
                    "Вимоги до відносного довірчого інтервалу одиничного вмісту упаковки витримуються, " +
                            "однорідність РСЗ підтверджена",
                    "Требования к относительному доверительному интервалу единичного содержания упаковки " +
                            "выдерживаются, однородность РСО подтверждена"),
            msTodiesunvOnp = new MultiString(
                    "Requirements for the relative confidence interval of the unit content of the pack are " +
                            "not satisfied, the homogeneity of WRS is not confirmed",
                    "Вимоги до відносного довірчого інтервалу одиничного вмісту упаковки не витримуються, " +
                            "однорідність РСЗ не підтверджена",
                    "Требования к относительному доверительному интервалу единичного содержания упаковки не " +
                            "выдерживаются, однородность РСО не подтверждена"),
            msORSO = new MultiString("Homogeneity of WRS", "Однородність РСЗ", "Однородность РСО"),
            msConfirmed = new MultiString("confirmed", "підтверджена", "подтверждена"),
            msNotConfirmed = new MultiString("not confirmed", "не підтверджена", "не подтверждена"),
            msOfSubstance = new MultiString("of substance", "субстанції", "субстанции"),
            msWeight = new MultiString("Weight", "Навіска", "Навеска"),
            ms_mg = new MultiString("mg", "мг", "мг"),
            msRequirementsForTheResultsQualityCriteriaAreSatisfied = new MultiString(
                    "Requirements for the results quality criteria are satisfied",
                    "Вимоги до критеріїв прийнятності для якості отриманих результатів витримуються",
                    "Требования к критериям приемлемости для качества полученных результатов выдерживаются"),
            msRequirementsForTheResultsQualityCriteriaAreNotSatisfied = new MultiString(
                    "Requirements for the results quality criteria are not satisfied",
                    "Вимоги до критеріїв прийнятності для якості отриманих результатів не витримуються",
                    "Требования к критериям приемлемости для качества полученных результатов не выдерживаются"),
            msNotes = new MultiString("Notes", "Примітки", "Примечания"),
            msWarning = new MultiString("Warning:", "Попередження:", "Предупреждение:"),
            msPage = new MultiString("Page ", "Сторінка ", "Страница "),
            msPageOf = new MultiString("Page %d of %d", "Cторінка %d з %d", "Cтраница %d из %d"),
            msFrom = new MultiString(" from ", " з ", " из "),
            msNameOfSolution = new MultiString("Name of solution", "Назва розчину", "Название раствора"),
            msAnalyticalSignal = new MultiString("Analytical signal", "Аналітичний сигнал", "Аналитический сигнал"),
            msMeasurementRuntime = new MultiString(
                    "Measurement runtime", "Час виконання вимірювання", "Время выполнения измерения"),
            msCalcVariancesEquality = new MultiString(
                    "Calculate of variances equality",
                    "Розраховувати равноточніcть дисперсій",
                    "Рассчитывать равноточность дисперсий"),
            msHomogeneity = new MultiString(
                    "Homogeneity",
                    "Однорідність",
                    "Однородность"),
            msCalcSamplesHomogeneity = new MultiString(
                    "Calculate the homogeneity of samples",
                    "Розраховувати однорідність вибірок",
                    "Рассчитывать однородность выборок"),
            msCalcDrift = new MultiString("Calculate drift", "Розраховувати дрейф", "Рассчитывать дрейф"),
            msUseStudent_sTest = new MultiString(
                    "Use Student's test for homogeneity of the WRS (P = 95%)",
                    "Використовувати критерій Стьюдента для однорідності РСЗ (P = 95%)",
                    "Использовать критерий Стьюдента для однородности РСО (P = 95%)"),
            msOptions = new MultiString("Options", "Опції", "Опции"),
            msCertifiedValueOfRSIsMissed = new MultiString(
                    "Certified value of RS is missed",
                    "Відсутнє атестоване значення СЗ",
                    "Отсутствует аттестованное значение СО"),
            msRequirementToUncertaintySortedOfWRSIsMissed = new MultiString(
                    "Requirement to uncertaintySorted of WRS is missed",
                    "Відсутні вимоги до невизначеності РСЗ",
                    "Отсутствуют требования к неопределенности РСО"),
            msOTKNPCOAZNR = new MultiString(
                    "Requirements to uncertaintySorted of WRS are MISSED. WRS certified value cannot be calculated",
                    "Відсутні вимоги до невизначеності РСЗ. Атестоване значення РСЗ не розраховується",
                    "Отсутствуют требования к к неопределенности РСО. Аттестованное значение РСО не рассчитывается"),
            msOAZCOAZNR = new MultiString(
                    "Certified value of RS is missed. WRS certified value cannot be calculated",
                    "Відсутнє атестоване значення СЗ. Атестоване значення РСЗ не розраховується",
                    "Отсутствует аттестованное значение СО. Аттестованное значение РСО не рассчитывается"),
            msNoRSData = new MultiString("No RS data", "Відсутні дані СЗ", "Отсутствуют данные СО"),
            msNoWRSData = new MultiString(
                    "No WRS data. Calculations cannot be performed",
                    "Відсутні дані РСЗ. Розрахунки не здійснюються",
                    "Отсутствуют данные РСО. Расчеты не проводятся"),
            msDifferentNumberOfParallelMeasurementsUsedForDifferentSamples = new MultiString(
                    "Different number of parallel measurements used for different samples",
                    "Для різних вибірок використовується різне число паралельних вимірювань",
                    "Для разных выборок используется различное число параллельных измерений"),
            msNoRSDataRSCertifiedValueIsNotCalculated = new MultiString(
                    "No RS data. RS certified value is not calculated",
                    "Відсутні дані СЗ. Атестоване значення РСЗ не розраховується",
                    "Отсутствуют данные СО. Аттестованное значение РСО не рассчитывается"),
            msTheOnlyOneRSSampleIsUsedTheDifferenceCannotBeChecked = new MultiString(
                    "The only one RS sample is used. The difference between the mean values for the RS cannot be checked",
                    "Використовується тільки одна вибірка СЗ. Різниця середніх значень для СЗ не перевіряється",
                    "Используется только одна выборка СО. Различие средних значений для СО не проверяется"),
            msTheNumberOfRSSamplesIsGreaterThanTwo = new MultiString(
                    "The number of RS samples is greater than 2. Calculations cannot be performed",
                    "Кількість вибірок СЗ більше 2. Розрахунки не здійснюються",
                    "Количество выборок СО более 2. Расчеты не проводятся"),
            msTheNumberOfWRSSamplesIsGreaterThanTwo = new MultiString(
                    "The number of WRS samples is less than 2. Calculations cannot be performed",
                    "Кількість вибірок РСЗ менше 2. Розрахунки не здійснюються",
                    "Количество выборок РСО менее 2. Расчеты не проводятся"),
            msAnalysisTimeIsMissedDriftCannotBeCalculated = new MultiString(
                    "Analysis time is missed. Drift cannot be calculated",
                    "Відсутній час виконання аналізу. Дрейф не розраховується",
                    "Отсутствует время выполнения анализа. Дрейф не рассчитывается"),
            msInsufficientCountOfParallelMeasurements = new MultiString(
                    "Insufficient count of parallel measurements",
                    "Недостатня кількість паралельних вимірювань",
                    "Недостаточное количество параллельных измерений"),
            msNoParallelMeasurements = new MultiString(
                    "No parallel measurements",
                    "Немає паралельних вимірювань",
                    "Нет параллельных измерений"),
            msDriftCannotBeCalculated = new MultiString(
                    "Drift cannot be calculated",
                    "Дрейф не розраховується",
                    "Дрейф не рассчитывается"),
            msCheckingTheHomogeneityCannotBeCalculated = new MultiString(
                    "Checking the homogeneity cannot be calculated",
                    "Перевірка однорідності не розраховується",
                    "Проверка однородности не рассчитывается"),
            msWrongTimes = new MultiString(
                    "Wrong analysis time. Drift cannot be calculated",
                    "Час виконання аналізу задано неправильно. Дрейф не розраховується",
                    "Время выполнения анализа заданно неправильно. Дрейф не рассчитывается"),
            msNumberOfWrsAndRsTestsDoesNotMatchTheChosenSchema = new MultiString(
                    "Number of WRS and RS tests does not match the chosen schema",
                    "Кількість випробувань РСО і СО не відповідає вибраній схемі",
                    "Количество испытаний РСО и СО не соответствует выбранной схеме"),
            msSaveSuccess = new MultiString("Save Successful", "Дані успішно збережені", "Данные успешно сохранены"),
            msSaveResultsSuccess = new MultiString(
                    "Save Successful",
                    "Результати успішно збережені",
                    "Результаты успешно сохранены"),
            msFileWriteError = new MultiString("File write error", "Помилка запису в файл", "Ошибка записи в файл"),
            msShowTimeValues = new MultiString(
                    "Show time values", "Показувати значення часу", "Показывать значения времени"),
            msAddSample = new MultiString("Add Sample", "Додати вибоку", "Добавить выборку"),
            msRemoveSample = new MultiString("Remove Sample", "Видалити виборку", "Удалить выборку"),
            msAdd = new MultiString("Add", "Додати", "Добавить"),
            msRemove = new MultiString("Remove", "Видалити", "Удалить"),
            msRowDoesNotSelected = new MultiString(
                    "Row does not selected", "Рядок для видалення не обраний", "Строка для удаления не выбрана"),
            msRowIsNotEmpty = new MultiString(
                    "Row is not empty", "Рядок для видалення не обраний", "Строка для удаления не выбрана"),
            msCurrentSampleWillBeDeleted = new MultiString(
                    "Current sample will be deleted. Are you sure?",
                    "Поточну вибірку буде видалено. Ви впевнені?",
                    "Текущая выборка будет удалена. Вы уверены?"),
            msLastSampleWillBeDeleted = new MultiString(
                    "Last sample will be deleted. Are you sure?",
                    "Останню вибірку буде видалено. Ви впевнені?",
                    "Последняя выборка будет удалена. Вы уверены?"),
            msDeletingSample = new MultiString("Deleting sample", "Видалення вибірки", "Удаление выборки"),
            msCurrentRowWillBeDeleted = new MultiString(
                    "Current row of values will be deleted. Are you sure?",
                    "Поточний рядок даних буде видалено. Ви впевнені?",
                    "Текущая строка данных будет удалена. Вы уверены?"),
            msDeletingRow = new MultiString("Deleting row", "Видалення рядка", "Удаление строки"),
            msAboutTitle = new MultiString("About", "Про програму", "О программе"),
            msAboutText = new MultiString("Version ", "Версія ", "Версия "),
            msAllRightsReserved = new MultiString("All rights reserved", "Усі права належать", "Все права принадлежат"),
            msError = new MultiString("Error", "Помилка", "Ошибка"),
            msWrongValueOfXPSS = new MultiString(
                    "Wrong value of XPSS",
                    "Хибне значення атестованого значення СЗ",
                    "Неправильное значение аттестованного значения СО"),
            msWrongValueOfX = new MultiString(
                    "Wrong value of X",
                    "Хибне значення X",
                    "Неправильное значение X"),
            msWrongValueOfConcentration = new MultiString(
                    "Wrong value of Concentration",
                    "Хибне значення констрації",
                    "Неправильное значение концентрации"),
            msWrongValueOfUncertainty = new MultiString(
                    "Wrong value of Uncertainty",
                    "Хибне значення невизначеності",
                    "Неправильное значение неопределенности"),
            msWrongValueOfDeltaWRS = new MultiString(
                    "Wrong value of requirements for the uncertainties of certified values",
                    "Хибне значення вимог до невизначеності атестованого значення РСЗ",
                    "Неправильное значение требований к неопределенности аттестованного значения РСО"),
            msAttestationWasNotDone = new MultiString(
                    "Attestation was not done",
                    "Атестація не була виконана",
                    "Аттестация не была выполнена"),
            msRS = new MultiString("RS", "СЗ", "СО"),
            msDataNotSaved = new MultiString(
                    "Data not saved. Save?", "Дані не збережено. Зберегти?", "Данные не сохранены. Сохранить?"),
            msReportNotSaved = new MultiString(
                    "Report not saved. Save?", "Звіт не збережено. Зберегти?", "Отчет не сохранен. Сохранить?"),
            msAttention = new MultiString("Attention!", "Увага!", "Внимание!");
}
