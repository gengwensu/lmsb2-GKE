import React, { useState, useEffect } from 'react';
import ApiClient from '../services/ApiClient';

export function ChallengeComponent() {
    const [factorA, setFactorA] = useState('');
    const [factorB, setFactorB] = useState('');
    const [user, setUser] = useState('');
    const [message, setMessage] = useState('');
    const [guess, setGuess] = useState(0);
    useEffect(() => {
        ApiClient.challenge().then((res) => {
            if (res.ok) {
                res.json().then((json) => {
                    setFactorA(json.factorA);
                    setFactorB(json.factorB);
                });
            } else setMessage('Error: server error or not available');
        });
    }, [user]);
    const handleSubmitResult = (e: React.SyntheticEvent) => {
        e.preventDefault();
        ApiClient.sendGuess(user, factorA, factorB, guess).then((res) => {
            if (res.ok) {
                res.json().then((json) => {
                    if (json.correct) setMessage('Congratulations! Your guess is correct');
                    else setMessage('Oops! Your guess ' + json.resultAttempt + ' is wrong, but keep playing!');
                });
            }
        });
    };

    return (
        <div>
            <div>
                <h3>Your new challenge is</h3>
                <h1>
                    {factorA} x {factorB}
                </h1>
            </div>
            <form onSubmit={handleSubmitResult}>
                <label>
                    Your alias:
                    <input
                        type="text"
                        maxLength={12}
                        name="user"
                        value={user}
                        onChange={(e) => setUser(e.target.value)}
                    />
                </label>
                <br />
                <label>
                    Your guess:
                    <input
                        type="number"
                        min="0"
                        name="guess"
                        value={guess}
                        onChange={(e) => setGuess(e.target.value)}
                    />
                </label>
                <br />
                <input type="submit" value="Submit" />
            </form>
            <h4>{message}</h4>
        </div>
    );
}
