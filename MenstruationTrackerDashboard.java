import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class MenstruationTrackerDashboard {
    // ANSI color codes for terminal styling
    private static final String RESET = "\u001B[0m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String CYAN = "\u001B[36m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String PINK_BACKGROUND = "\u001B[48;5;218m";
    private static final String LIGHT_PURPLE_BG = "\u001B[48;5;225m";
    private static final String BOLD = "\u001B[1m";
    private static final String ITALIC = "\u001B[3m";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Print dashboard header
        System.out.println(PURPLE_BOLD + "╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║" + PINK_BACKGROUND + PURPLE_BOLD + "                     MENSTRUAL CYCLE TRACKER DASHBOARD                     " + RESET + PURPLE_BOLD + "║");
        System.out.println("║" + PINK_BACKGROUND + PURPLE_BOLD + "               Track your cycle with confidence & precision               " + RESET + PURPLE_BOLD + "║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
        
        // Get user input with styling
        System.out.println(LIGHT_PURPLE_BG + PURPLE_BOLD + "  ENTER YOUR CYCLE INFORMATION  " + RESET);
        System.out.print(BLUE_BOLD + "➤ Last period date (YYYY-MM-DD): " + RESET);
        String lastPeriodInput = scanner.nextLine();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lastPeriodDate = LocalDate.parse(lastPeriodInput, formatter);
        
        System.out.print(BLUE_BOLD + "➤ Average cycle length (days): " + RESET);
        int cycleLength = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        
        // Calculate dates
        LocalDate nextPeriodDate = lastPeriodDate.plusDays(cycleLength);
        LocalDate today = LocalDate.now();
        long daysLeft = ChronoUnit.DAYS.between(today, nextPeriodDate);
        
        // Calculate ovulation and fertility window
        LocalDate ovulationDate = lastPeriodDate.plusDays(cycleLength - 14);
        LocalDate fertileStart = ovulationDate.minusDays(3);
        LocalDate fertileEnd = ovulationDate.plusDays(3);
        
        // Display dashboard
        clearConsole();
        printDashboardHeader();
        
        // Print summary section
        System.out.println();
        System.out.println(LIGHT_PURPLE_BG + PURPLE_BOLD + "  CYCLE SUMMARY  " + RESET);
        System.out.println();
        
        System.out.println(CYAN + BOLD + "┌──────────────────────────────────────┬──────────────────────────────────────┐" + RESET);
        System.out.printf(CYAN + BOLD + "│ " + PURPLE_BOLD + "%-36s " + CYAN + BOLD + "│ " + PURPLE_BOLD + "%-36s " + CYAN + BOLD + "│\n" + RESET, 
                          "Last Period", "Cycle Length");
        System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-36s " + CYAN + BOLD + "│ " + GREEN_BOLD + "%-36s " + CYAN + BOLD + "│\n" + RESET, 
                          lastPeriodDate.format(formatter), cycleLength + " days");
        System.out.println(CYAN + BOLD + "├──────────────────────────────────────┼──────────────────────────────────────┤" + RESET);
        System.out.printf(CYAN + BOLD + "│ " + PURPLE_BOLD + "%-36s " + CYAN + BOLD + "│ " + PURPLE_BOLD + "%-36s " + CYAN + BOLD + "│\n" + RESET, 
                          "Next Predicted Period", "Days Until Next Period");
        System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-36s " + CYAN + BOLD + "│ " + (daysLeft > 3 ? GREEN_BOLD : RED_BOLD) + "%-36s " + CYAN + BOLD + "│\n" + RESET, 
                          nextPeriodDate.format(formatter), daysLeft + " days");
        System.out.println(CYAN + BOLD + "├──────────────────────────────────────┼──────────────────────────────────────┤" + RESET);
        System.out.printf(CYAN + BOLD + "│ " + PURPLE_BOLD + "%-36s " + CYAN + BOLD + "│ " + PURPLE_BOLD + "%-36s " + CYAN + BOLD + "│\n" + RESET, 
                          "Ovulation Date", "Fertility Window");
        System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-36s " + CYAN + BOLD + "│ " + GREEN_BOLD + "%-36s " + CYAN + BOLD + "│\n" + RESET, 
                          ovulationDate.format(formatter), fertileStart.format(formatter) + " to " + fertileEnd.format(formatter));
        System.out.println(CYAN + BOLD + "└──────────────────────────────────────┴──────────────────────────────────────┘" + RESET);
        
        // Print calendar view
        System.out.println();
        System.out.println(LIGHT_PURPLE_BG + PURPLE_BOLD + "  CYCLE CALENDAR  " + RESET);
        System.out.println();
        printCalendar(lastPeriodDate, nextPeriodDate, ovulationDate, fertileStart, fertileEnd);
        
        // Print cycle insights
        System.out.println();
        System.out.println(LIGHT_PURPLE_BG + PURPLE_BOLD + "  CYCLE INSIGHTS  " + RESET);
        System.out.println();
        printCycleInsights(daysLeft, ovulationDate, fertileStart, fertileEnd);
        
        // Print footer
        System.out.println();
        System.out.println(PURPLE_BOLD + ITALIC + "Note: This tool provides predictions based on average cycle length." + RESET);
        System.out.println(PURPLE_BOLD + ITALIC + "Individual experiences may vary. Consult a healthcare provider for medical advice." + RESET);
        System.out.println();
        System.out.println(PURPLE_BOLD + "════════════════════════════════════════════════════════════════════════" + RESET);
        
        scanner.close();
    }
    
    private static void printDashboardHeader() {
        System.out.println(PURPLE_BOLD + "╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║" + PINK_BACKGROUND + PURPLE_BOLD + "                     MENSTRUAL CYCLE TRACKER DASHBOARD                     " + RESET + PURPLE_BOLD + "║");
        System.out.println("║" + PINK_BACKGROUND + PURPLE_BOLD + "               Track your cycle with confidence & precision               " + RESET + PURPLE_BOLD + "║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.printf(BLUE_BOLD + "%-40s" + PURPLE_BOLD + "%-40s\n" + RESET, 
                          "  Today: " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                          "Day of Cycle: " + getDayOfCycle(LocalDate.now()));
    }
    
    private static int getDayOfCycle(LocalDate date) {
        // This would need your actual calculation based on last period
        return (int) (Math.random() * 28) + 1; // Placeholder
    }
    
    private static void printCalendar(LocalDate lastPeriod, LocalDate nextPeriod, 
                                     LocalDate ovulation, LocalDate fertileStart, 
                                     LocalDate fertileEnd) {
        System.out.println(CYAN + BOLD + "┌───────┬───────┬───────┬───────┬───────┬───────┬───────┐" + RESET);
        System.out.println(CYAN + BOLD + "│ Sun   │ Mon   │ Tue   │ Wed   │ Thu   │ Fri   │ Sat   │" + RESET);
        System.out.println(CYAN + BOLD + "├───────┼───────┼───────┼───────┼───────┼───────┼───────┤" + RESET);
        
        LocalDate date = LocalDate.now().withDayOfMonth(1);
        int daysInMonth = date.lengthOfMonth();
        int dayOfWeek = date.getDayOfWeek().getValue() % 7; // Sunday = 0
        
        // Print empty days for the first week
        for (int i = 0; i < dayOfWeek; i++) {
            System.out.print(CYAN + BOLD + "│       " + RESET);
        }
        
        // Print days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = date.withDayOfMonth(day);
            String display = String.format("%2d", day);
            String color = RESET;
            
            // Highlight today
            if (currentDate.equals(LocalDate.now())) {
                color = YELLOW_BOLD;
                display = "[" + day + "]";
            }
            
            // Highlight last period
            if (currentDate.equals(lastPeriod)) {
                color = RED_BOLD;
                display = "●" + day;
            }
            
            // Highlight next period
            if (currentDate.equals(nextPeriod)) {
                color = RED_BOLD;
                display = "★" + day;
            }
            
            // Highlight ovulation day
            if (currentDate.equals(ovulation)) {
                color = GREEN_BOLD;
                display = "⚤" + day;
            }
            
            // Highlight fertility window
            if (!currentDate.equals(ovulation) && 
                !currentDate.isBefore(fertileStart) && 
                !currentDate.isAfter(fertileEnd)) {
                color = GREEN_BOLD;
                display = "♥" + day;
            }
            
            System.out.print(CYAN + BOLD + "│ " + color + display + "   " + RESET);
            
            // Move to next line at end of week
            if ((dayOfWeek + day) % 7 == 0) {
                System.out.println(CYAN + BOLD + "│" + RESET);
                if (day < daysInMonth) {
                    System.out.println(CYAN + BOLD + "├───────┼───────┼───────┼───────┼───────┼───────┼───────┤" + RESET);
                }
            }
        }
        
        // Complete the last row
        int remaining = 7 - ((dayOfWeek + daysInMonth) % 7);
        if (remaining < 7) {
            for (int i = 0; i < remaining; i++) {
                System.out.print(CYAN + BOLD + "│       " + RESET);
            }
            System.out.println(CYAN + BOLD + "│" + RESET);
        }
        
        System.out.println(CYAN + BOLD + "└───────┴───────┴───────┴───────┴───────┴───────┴───────┘" + RESET);
        
        // Print legend
        System.out.println();
        System.out.println(PURPLE_BOLD + "Calendar Legend:" + RESET);
        System.out.println(YELLOW_BOLD + "  [##] - Today" + RESET);
        System.out.println(RED_BOLD + "  ●## - Last Period" + RESET);
        System.out.println(RED_BOLD + "  ★## - Next Predicted Period" + RESET);
        System.out.println(GREEN_BOLD + "  ♥## - Fertility Window" + RESET);
        System.out.println(GREEN_BOLD + "  ⚤## - Ovulation Day" + RESET);
    }
    
    private static void printCycleInsights(long daysLeft, LocalDate ovulation, 
                                          LocalDate fertileStart, LocalDate fertileEnd) {
        System.out.println(CYAN + BOLD + "┌────────────────────────────────────────────────────────────────────────┐" + RESET);
        
        if (daysLeft > 0) {
            System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "Your next period is expected in " + daysLeft + " days");
        } else if (daysLeft == 0) {
            System.out.printf(CYAN + BOLD + "│ " + RED_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "Your period is expected to start today!");
        } else {
            System.out.printf(CYAN + BOLD + "│ " + RED_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "The predicted date has passed. Consider updating your last period date");
        }
        
        long daysToOvulation = ChronoUnit.DAYS.between(LocalDate.now(), ovulation);
        if (daysToOvulation > 0) {
            System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "Ovulation is expected in " + daysToOvulation + " days (" + ovulation.format(DateTimeFormatter.ofPattern("MMM dd")) + ")");
        } else if (daysToOvulation == 0) {
            System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "Today is your expected ovulation day");
        } else {
            System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "Ovulation has passed " + (-daysToOvulation) + " days ago");
        }
        
        if (LocalDate.now().isBefore(fertileStart)) {
            long daysToFertile = ChronoUnit.DAYS.between(LocalDate.now(), fertileStart);
            System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "Your next fertility window starts in " + daysToFertile + " days");
        } else if (!LocalDate.now().isAfter(fertileEnd)) {
            System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "You are currently in your fertility window (" + 
                             fertileStart.format(DateTimeFormatter.ofPattern("MMM dd")) + " - " + 
                             fertileEnd.format(DateTimeFormatter.ofPattern("MMM dd")) + ")");
        } else {
            System.out.printf(CYAN + BOLD + "│ " + GREEN_BOLD + "%-72s " + CYAN + BOLD + "│\n" + RESET, 
                             "Your fertility window has passed");
        }
        
        System.out.println(CYAN + BOLD + "└────────────────────────────────────────────────────────────────────────┘" + RESET);
    }
    
    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print some newlines
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
}