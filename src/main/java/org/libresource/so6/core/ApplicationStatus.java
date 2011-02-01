package org.libresource.so6.core;

import java.util.Observer;

public class ApplicationStatus {

	public enum Action {
		COMMIT, UPDATE
	}

	public enum Task {
		BUILD_PATCH, DETECT_LOCAL_OPERATION, UPLOAD_PATCH, PATCH_REFERENCE_COPY, DOWNLOAD_PATCHES, MERGE, PATCH_LOCAL_COPY
	}
	
	public enum SubTask {
		WALKING_FOR_TYPE_DETECTION, WALKING_FOR_CHANGE_DETECTION, DETECTING_REMOVE
	}
	
	private static ApplicationStatus instance = new ApplicationStatus();

	public static ApplicationStatus getInstance() {
		return ApplicationStatus.instance;
	}

    /**
     * Notify an so6 action is started.
     *
     * @param actionType
     *            type of the started action.
     * @param expectedTaskCount
     *            the number of expected task ran during this action. -1 is passed if the task count is undetermined.
     */	
	public void actionStarted(Action actionType, int expectedTaskCount) {
	}

    /**
     * Notify an so6 action is terminated.
     *
     * @param actionType
     *            type of the terminated action.
     */	
	public void actionTerminated(Action actionType) {
	}

    /**
     * Notify an so6 task is started.
     *
     * @param taskType
     *            type of the started task.
     */		
	public void taskStarted(Task taskType) {	
	}
	
    /**
     * Notify an so6 task is terminated.
     *
     * @param taskType
     *            type of the terminated task.
     */
    public void taskTerminated(Task taskType) {	
    }
    
    /**
     * Notify an so6 sub-task is started.
     *
     * @param subTaskType
     *            type of the started sub-task.
     */
	public void subTaskStarted(SubTask subTaskType) {	
	}
	
    /**
     * Notify an so6 sub-task is terminated.
     *
     * @param subTaskType
     *            type of the terminated sub-task.
     */
    public void subTaskTerminated(SubTask subTaskType) {	
    }
    
    /**
     * Notify an so6 task is under progress.
     *
     * - when patches are downloaded (DOWNLOAD_PATCHES task)
	 * - when removed files are detected (DETECT_LOCAL_OPERATION task)
     * - when files are walked (DETECT_LOCAL_OPERATION task)
     * - when operations are serialized in the patch file (BUILD_PATCH task)
     * - when operations are executed (PATCH_LOCAL_COPY, PATCH_REFERENCE_COPY tasks)
     * - when operations are merged (MERGE task)
     *
     * @param min
     *            minimal bound.
     * @param max
     *            maximal bound.
     * @param current
     *            current value.
     */
    public void taskOnProgress(long min, long max, long current) {
	}
	
    
    private int criticalPhase = 0;
    
	// FIXME: only used for test purpose?
	public void criticalPhaseStarted() {
		this.criticalPhase++;
	}
	
	// FIXME: only used for test purpose?
	public int getCurrentCriticalPhaseNumber() {
		return this.criticalPhase;
	}

	// FIXME: only used for test purpose (at the moment)?
	public void addObserver(Observer threadKiller) {
	}

}
