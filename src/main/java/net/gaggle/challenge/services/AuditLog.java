package net.gaggle.challenge.services;

import java.util.function.Supplier;

public interface AuditLog {
    /**
     * Decorate an action and record audit information about the action.
     * @param actionName helpful name to give the operation.
     * @param action the actual controller operation.
     * @param <T> whatever type that will be the return value
     * @return whatever value you wish to return to the caller.
     */
    <T> T auditAction(String actionName, Supplier<T> action);
}
