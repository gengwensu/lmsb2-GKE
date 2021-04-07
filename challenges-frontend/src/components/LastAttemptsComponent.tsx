import React from 'react';
import {Attempt} from '../shared/types';

export function LastAttemptsComponent(lastAttempts: Attempt[]) {
    return (
        <table>
            <thead>
            <tr>
                <th>Challenge</th>
                <th>Your guess</th>
                <th>Correct</th>
            </tr>
            </thead>
            <tbody>
            {lastAttempts.map(a =>
                <tr key={a.id}
                    style={{ color: a.correct ? 'green' : 'red' }}>
                    <td>{a.factorA} x {a.factorB}</td>
                    <td>{a.resultAttempt}</td>
                    <td>{a.correct ? "Correct" : ("Incorrect (" + a.factorA * a.factorB + ")")}</td>
                </tr>
            )}
            </tbody>
        </table>
    );
}