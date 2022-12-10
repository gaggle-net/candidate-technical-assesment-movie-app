package net.gaggle.challenge.services.filelog;

import net.gaggle.challenge.services.AuditLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Simple implementation that periodically flushes the audit logs to a disk file.  ( Yeah it's sorta lame we're
 * doing it to /var/tmp, but we gotta draw the example line somewhere right? )
 */
@Component
@Scope("singleton")
public class FileRecordingAuditLog implements AuditLog {

    /**
     * LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileRecordingAuditLog.class);

    private Queue<AuditInfo> auditLog = new LinkedBlockingQueue<>();

    /**
     * Location to receive the audit info
     */
    private File targetFile;

    /**
     * Creates the target location.  For now this is temp
     * @throws IOException
     */
    public FileRecordingAuditLog() throws IOException {
        targetFile = Files.createTempFile("codeTest", ".txt").toFile();
        LOG.info("Audit Logs will be available at {}", targetFile.getAbsolutePath() );
    }


    /**
     * Decorates the supplied action by capturing and tracing execution times and actions for target logs.
     * @param actionName helpful name to give the operation.
     * @param action the actual controller operation.
     * @param <T>
     * @return value that the action returned unmodified.
     */
    @Override
    public <T> T auditAction(final String actionName, final Supplier<T> action) {
        LOG.debug("Beginning exceution of {}", actionName);
        long startTime = System.currentTimeMillis();
        boolean error = false;
        RuntimeException exception = null;
        T result = null;

        try {
            result = action.get();
        } catch (RuntimeException e) {
            exception = e;
            error = true;
        }
        long finalTime = System.currentTimeMillis();

        auditLog.add(new AuditInfo(actionName,
                finalTime - startTime,
                error,
                exception));

        //Rethrow after catching.
        if (exception != null) {
            throw exception;
        }

        return result;
    }

    /**
     * Send the pending audit log messages to disk and clear the queue.
     * Flushes every 30 seconds.
     */
    @Scheduled(fixedRate =  30_000)
    public void flushToDisk() throws IOException {
        LOG.info("Flushing Audit Log");
        //Make a copy before we flush to disk for thread safety
        List<AuditInfo> itemsToFlushToDisk = new ArrayList<>(auditLog);
        auditLog = new LinkedBlockingQueue<>();
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(targetFile))) {
        	
            String auditDetails = new ArrayList<>(itemsToFlushToDisk)
            		.stream()
            		.map(AuditInfo::toString)
            		.collect(Collectors.joining("\n"));
            
            try {
                osw.write(auditDetails);
                osw.write('\n');
            } catch (IOException e) {
                LOG.error("Error writing audit log to disk!", e);
                auditLog.addAll(itemsToFlushToDisk);
            }
        }

    }

    /**
     * Data structure that holds all the bits.  toString() will return  a CSV formatted version of this operation.
     */
    static class AuditInfo {
        private final String action;
        private final long timeToComplete;
        private final boolean error;
        private final Throwable t;


        public AuditInfo(String action, long timeToComplete, boolean error, Throwable t) {
            this.action = action;
            this.timeToComplete = timeToComplete;
            this.error = error;
            this.t = t;
        }

        @Override
        public String toString() {
            return String.format("%s, %d, %s, %s", action, timeToComplete, error, t);
        }
    }
}
