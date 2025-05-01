package utils;

    public class ScenarioContext {
        // Using ThreadLocal to ensure the step name is specific to the current thread
        private static final ThreadLocal<String> currentStepName = new ThreadLocal<>();

        // Set the current step name (called within step definition)
        public static void setCurrentStepName(String stepName) {
            currentStepName.set(stepName);
        }

        // Get the current step name (called when logging the step)
        public static String getCurrentStepName() {
            return currentStepName.get();
        }

        // Clear the current step name after each step or scenario completion
        public static void clear() {
            currentStepName.remove();
        }


}
