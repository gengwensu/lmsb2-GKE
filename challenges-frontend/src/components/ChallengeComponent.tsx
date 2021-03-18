import React, { useState, useEffect } from 'react';
import ApiClient from '../services/ApiClient';

const initValues = {
    factorA: '',
    factorB: '',
    user: '',
    message: '',
    guess: 0,
};
export function ChallengeComponent() {
    const [values, setValues] = useState(initValues);

    useEffect(() => {
        ApiClient.challenge().then((res) => {
            if (res.ok) {
                res.json().then((json) => {
                    setValues({
                        ...values,
                        factorA: json.factorA,
                        factorB: json.factorB,
                    });
                });
            } else setValues({ ...values, message: 'Error: server error or not available' });
        });
    }, [values.user]);

    const handleSubmitResult = (e: React.SyntheticEvent) => {
        e.preventDefault();
        ApiClient.sendGuess(values.user, values.factorA, values.factorB, values.guess).then((res) => {
            if (res.ok) {
                res.json().then((json) => {
                    if (json.correct) setValues({ ...values, message: 'Congratulations! Your guess is correct' });
                    else
                        setValues({
                            ...values,
                            message: 'Oops! Your guess ' + json.resultAttempt + ' is wrong, but keep playing!',
                        });
                });
            }
        });
    };

    const handleChange = (e: React.changeEvent) => setValues({ ...values, [e.target.name]: e.target.value });

    return (
        <div>
            <div>
                <h3>Your new challenge is</h3>
                <h1>
                    {values.factorA} x {values.factorB}
                </h1>
            </div>
            <form onSubmit={handleSubmitResult}>
                <label>
                    Your alias:
                    <input type="text" maxLength={12} name="user" value={values.user} onChange={handleChange} />
                </label>
                <br />
                <label>
                    Your guess:
                    <input type="number" min="0" name="guess" value={values.guess} onChange={handleChange} />
                </label>
                <br />
                <input type="submit" value="Submit" />
            </form>
            <h4>{values.message}</h4>
        </div>
    );
}
