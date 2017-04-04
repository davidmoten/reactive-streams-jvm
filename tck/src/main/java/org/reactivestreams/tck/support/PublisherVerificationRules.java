/************************************************************************
 * Licensed under Public Domain (CC0)                                    *
 *                                                                       *
 * To the extent possible under law, the person who associated CC0 with  *
 * this code has waived all copyright and related or neighboring         *
 * rights to this code.                                                  *
 *                                                                       *
 * You should have received a copy of the CC0 legalcode along with this  *
 * work. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.*
 ************************************************************************/

package org.reactivestreams.tck.support;

/**
 * Internal TCK use only.
 * Add / Remove tests for PublisherVerification here to make sure that they arre added/removed in the other places.
 */
public interface PublisherVerificationRules {
  /**
   * Validates that the override of {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()}
   * returns a non-negative value.
   * @throws Exception allow checked exceptions to be thrown by the implementation of this test
   */
  void required_validate_maxElementsFromPublisher() throws Exception;
  /**
   * Validates that the override of {@link org.reactivestreams.tck.PublisherVerification#boundedDepthOfOnNextAndRequestRecursion()}
   * returns a positive value.
   * @throws Exception allow checked exceptions to be thrown by the implementation of this test
   */
  void required_validate_boundedDepthOfOnNextAndRequestRecursion() throws Exception;
  /**
   * Asks for a {@code Publisher} that should emit exaclty one item and complete (both within a
   * timeout specified by {@link org.reactivestreams.tck.TestEnvironment#defaultTimeoutMillis()}).
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} returns zero.
   * <p>This test mostly ensures that the {@code Publisher} implementation is actually operational.
   * If this test fails (likely with a timeout error), the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code Publisher.subscribe(Subscriber)} method has actual implementation,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, if there is an upstream {@code Publisher},
   * that {@code Publisher} is actually subscribed to,</li>
   * <li>if the {@code Publisher} is part of a chain, all elements actually issue a {@code request()} call
   * in response to the test subscriber or by default to their upstream,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, the {@code Subscriber.onSubscribe} is called
   * as part of the preparation process (usually before subscribing to other {@code Publisher}s),</li>
   * <li>if the {@code Publisher} implementation works for a consumer that calls {@code request(1)},</li>
   * <li>if the {@code Publisher} implementation is able to emit an {@code onComplete} without requests.</li> 
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_createPublisher1MustProduceAStreamOfExactly1Element() throws Throwable;
  /**
   * Asks for a {@code Publisher} that should emit exaclty three items and complete (all within a
   * timeout specified by {@link org.reactivestreams.tck.TestEnvironment#defaultTimeoutMillis()}).
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>This test mostly ensures that the {@code Publisher} implementation is actually operational.
   * If this test fails (likely with a timeout error), the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code Publisher.subscribe(Subscriber)} method has actual implementation,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, if there is an upstream {@code Publisher},
   * that {@code Publisher} is actually subscribed to,</li>
   * <li>if the {@code Publisher} is part of a chain, all elements actually issue a {@code request()} call
   * in response to the test subscriber or by default to their upstream,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, the {@code Subscriber.onSubscribe} is called
   * as part of the preparation process (usually before subscribing to other {@code Publisher}s),</li>
   * <li>if the {@code Publisher} implementation works for a consumer that calls {@code request(1)} after consuming an item,</li>
   * <li>if the {@code Publisher} implementation is able to emit an {@code onComplete} without requests.</li> 
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_createPublisher3MustProduceAStreamOfExactly3Elements() throws Throwable;
  /**
   * Asks for a {@code Publisher} that responds to a request pattern of 0 (not requesting upfront), 1, 1 and 2
   * in a timely manner. 
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 5.
   * <p>This test ensures that the {@code Publisher} implementation correctly responds to {@code request()} calls that in
   * total are less than the number of elements this {@code Publisher} could emit (thus the completion event won't be emitted).
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec101_subscriptionRequestMustResultInTheCorrectNumberOfProducedElements() throws Throwable;
  /**
   * Asks for a short {@code Publisher} and verifies that requesting once and with more than the length (but bounded) results in the
   * correct number of items to be emitted (i.e., length 3 and request 10) followed by an {@code onComplete} signal. 
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>This test ensures that the {@code Publisher} implementation can deal with larger requests than the number of items it can produce.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec102_maySignalLessThanRequestedAndTerminateSubscription() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (i.e., length 10), repeatedly subscribes to this {@code Publisher}, requests items
   * one by one and verifies the {@code Publisher} calls the {@code onXXX} methods non-overlappingly.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 10.
   * <p>Note that this test is probabilistic, that is, may not capture any concurrent invocation in a {code Publisher} implementation.
   * Note also that this test is sensitive to cases when a {@code request()} call in {@code onSubscribe()} triggers an asynchronous
   * call to the other {@code onXXX} methods. In contrast, the test allows synchronous call chain of 
   * {@code onSubscribe -> request -> onNext}.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if a {@code request()} call from {@code onSubscribe()} could trigger an asynchronous call to {@code onNext()} and if so, make sure
   * such {@code request()} calls are deferred until the call to {@code onSubscribe()} returns normally.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void stochastic_spec103_mustSignalOnMethodsSequentially() throws Throwable;
  /**
   * Verifies that if the call to {@code PublisherVerification.createErrorPublisher()} returns a non-null {@code Publisher},
   * it calls {@code onSubscribe} exactly once followed by a single call to {@code onError()} without issuing any requests and otherwise
   * not throwing any exception.
   * <p>If this test fails, the following could be checked within the error {@code Publisher} implementation:
   * <ul>
   * <li>the {@code Publisher.subscribe(Subscriber)} method has actual implementation,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, if there is an upstream {@code Publisher},
   * that {@code Publisher} is actually subscribed to,</li>
   * <li>if the {@code Publisher} implementation is able to emit an {@code onError} without requests,</li> 
   * <li>if the {@code Publisher} is non-empty as this test requires a {@code Publisher} without items.</li> 
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void optional_spec104_mustSignalOnErrorWhenFails() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (i.e., length 3) and verifies, after requesting one by one, the sequence
   * completes normally.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>Note that the tests requests 1 after the items have been received and before expecting an {@code onComplete} signal.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec105_mustSignalOnCompleteWhenFiniteStreamTerminates() throws Throwable;
  /**
   * Asks for an empty {@code Publisher} (i.e., length 0) and verifies it completes in a timely manner.
   * <p>Note that the tests requests 1 before expecting an {@code onComplete} signal.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>if the {@code Publisher} is non-empty as this test requires a {@code Publisher} without items.</li> 
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void optional_spec105_emptyStreamMustTerminateBySignallingOnComplete() throws Throwable;
  /**
   * Currently, this test is skipped because it is unclear this rule can be effectively checked
   * on a {@code Publisher} instance without looking into or hooking into the implementation of it.
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void untested_spec106_mustConsiderSubscriptionCancelledAfterOnErrorOrOnCompleteHasBeenCalled() throws Throwable;
  /**
   * Asks for a single-element {@code Publisher} and checks if requesting after the terminal event doesn't
   * lead to more items or terminal signals to be emitted.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 1.
   * <p>The tests requests 10 items upfront and 10 items after its completion.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>the indication for the terminal state is properly persisted and a request call can't trigger emission of more items or another
   * terminal signal.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec107_mustNotEmitFurtherSignalsOnceOnCompleteHasBeenSignalled() throws Throwable;
  /**
   * Currently, this test is skipped, although it is possible to validate an error {@code Publisher} along
   * the same lines as {@link #required_spec107_mustNotEmitFurtherSignalsOnceOnCompleteHasBeenSignalled()}.
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void untested_spec107_mustNotEmitFurtherSignalsOnceOnErrorHasBeenSignalled() throws Throwable;
  /**
   * Currently, this test is skipped as the expected behavior is currently optional: cancelling a sequence just
   * before the terminal event may or may not signal that terminal event.
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void untested_spec108_possiblyCanceledSubscriptionShouldNotReceiveOnErrorOrOnCompleteSignals() throws Throwable;
  /**
   * Asks for an empty {@code Publisher} and calls and verifies if {@code onSubscribe} was called before any calls
   * to the other {@code onXXX} methods.
   * <p>Note that this test doesn't request anything yet an {@code onNext} is not considered as a failure.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>the {@code Publisher.subscribe(Subscriber)} method has actual implementation,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, if there is an upstream {@code Publisher},
   * that {@code Publisher} is actually subscribed to,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, the {@code Subscriber.onSubscribe} is called
   * as part of the preparation process (usually before subscribing to other {@code Publisher}s).</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec109_mustIssueOnSubscribeForNonNullSubscriber() throws Throwable;
  /**
   * Currently, this test is skipped because there is no common agreement on what is to be considered a fatal exception and
   * besides, {@code Publisher.subscribe} is only allowed throw a {@code NullPointerException} and any other
   * exception would require looking into or hooking into the implementation of the {@code Publisher}.
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void untested_spec109_subscribeShouldNotThrowNonFatalThrowable() throws Throwable;
  /**
   * Asks for an empty {@code Publisher} and calls {@code subscribe} on it with {@code null} that should result in
   * a {@code NullPointerException} to be thrown.
   * <p>If this test fails, check if the {@code subscribe()} implementation has an explicit null check (or a method dereference
   * on the {@code Subscriber}), especially if the incoming {@code Subscriber} is wrapped or stored to be used later.
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec109_subscribeThrowNPEOnNullSubscriber() throws Throwable;
  /**
   * Verifies that if the call to {@code PublisherVerification.createErrorPublisher()} returns a non-null {@code Publisher},
   * it calls {@code onSubscribe} exactly once followed by a single call to {@code onError()} without issuing any requests.
   * <p>The difference between this test and {@link #optional_spec104_mustSignalOnErrorWhenFails()} is that there is
   * no explicit verification if exceptions were thrown in addition to the regular {@code onSubscribe+onError} signal pair.
   * <p>If this test fails, the following could be checked within the error {@code Publisher} implementation:
   * <ul>
   * <li>the {@code Publisher.subscribe(Subscriber)} method has actual implementation,</li>
   * <li>in the {@code Publisher.subscribe(Subscriber)} method, if there is an upstream {@code Publisher},
   * that {@code Publisher} is actually subscribed to,</li>
   * <li>if the {@code Publisher} implementation is able to emit an {@code onError} without requests,</li> 
   * <li>if the {@code Publisher} is non-empty as this test expects a {@code Publisher} without items.</li> 
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec109_mayRejectCallsToSubscribeIfPublisherIsUnableOrUnwillingToServeThemRejectionMustTriggerOnErrorAfterOnSubscribe() throws Throwable;
  /**
   * Currently, this test is skipped because enforcing rule §1.10 requires unlimited retention and reference-equal checks on
   * all incoming {@code Subscriber} which is generally infeasible, plus reusing the same {@code Subscriber} instance is
   * better detected (or ignored) inside {@code Subscriber.onSubscribe} when the method is called multiple times. 
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void untested_spec110_rejectASubscriptionRequestIfTheSameSubscriberSubscribesTwice() throws Throwable;
  /**
   * Ask for a single-element {@code Publisher} and subscribes to it twice, without consuming with either {@code Subscriber} instance
   * (i.e., no requests are issued).
   * <p>Note that this test ignores what signals the {@code Publisher} emits. Any exception thrown through non-regular
   * means will indicate a skipped test.
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void optional_spec111_maySupportMultiSubscribe() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 5), subscribes 3 {@code Subscriber}s to it, requests with different
   * patterns and checks if all 3 received the same events in the same order.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 5.
   * <p>The request pattern for the first {@code Subscriber} is (1, 1, 2, 1); for the second is (2, 3) and for the third is (3, 1, 1).
   * <p>Note that this test requires a {@code Publisher} that always emits the same signals to any {@code Subscriber}, regardless of
   * when they subscribe and how they request elements. I.e., a "live" {@code Publisher} emitting the current time would not pass this test.
   * <p>Note that this test is optional and may appear skipped even if the behavior should be actually supported by the {@code Publisher},
   * see the skip message for an indication of this. 
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void optional_spec111_multicast_mustProduceTheSameElementsInTheSameSequenceToAllOfItsSubscribersWhenRequestingOneByOne() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 3), subscribes 3 {@code Subscriber}s to it, requests more than the length items
   * upfront with each and verifies they all received the same items in the same order (but does not verify they all complete).
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>Note that this test requires a {@code Publisher} that always emits the same signals to any {@code Subscriber}, regardless of
   * when they subscribe and how they request elements. I.e., a "live" {@code Publisher} emitting the current time would not pass this test.
   * <p>Note that this test is optional and may appear skipped even if the behavior should be actually supported by the {@code Publisher},
   * see the skip message for an indication of this. 
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void optional_spec111_multicast_mustProduceTheSameElementsInTheSameSequenceToAllOfItsSubscribersWhenRequestingManyUpfront() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 3), subscribes 3 {@code Subscriber}s to it, requests more than the length items
   * upfront with each and verifies they all received the same items in the same order followed by an {@code onComplete} signal.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>Note that this test requires a {@code Publisher} that always emits the same signals to any {@code Subscriber}, regardless of
   * when they subscribe and how they request elements. I.e., a "live" {@code Publisher} emitting the current time would not pass this test.
   * <p>Note that this test is optional and may appear skipped even if the behavior should be actually supported by the {@code Publisher},
   * see the skip message for an indication of this. 
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void optional_spec111_multicast_mustProduceTheSameElementsInTheSameSequenceToAllOfItsSubscribersWhenRequestingManyUpfrontAndCompleteAsExpected() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 6), requests several times from within {@code onSubscribe} and then requests
   * one-by-one from {@code onNext}.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 6.
   * <p>The request pattern is 3 x 1 from within {@code onSubscribe} and one after each {@code onNext} signal.
   * <p>The test consumes the {@code Publisher} but otherwise doesn't verify the {@code Publisher} completes (however, it checks
   * for errors).
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec302_mustAllowSynchronousRequestCallsFromOnNextAndOnSubscribe() throws Throwable;
  /**
   * Asks for a {@code Publisher} with length equal to the value returned by {@link #required_validate_boundedDepthOfOnNextAndRequestRecursion()} plus 1,
   * calls {@code request(1)} externally and then from within {@code onNext} and checks if the stack depth did not increase beyond the
   * amount permitted by {@link #required_validate_boundedDepthOfOnNextAndRequestRecursion()}.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 
   * {@link #required_validate_boundedDepthOfOnNextAndRequestRecursion()} plus 1.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>the implementation doesn't allow unbounded recursion when {@code request()} is called from within {@code onNext}, i.e., the lack of
   * reentrant-safe state machine around the request amount (such as a for loop with a bound on the parameter {@code n} that calls {@code onNext}).
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec303_mustNotAllowUnboundedRecursion() throws Throwable;
  /**
   * Currently, this test is skipped because a {@code request} could enter into a synchronous computation via {@code onNext}
   * legally and otherwise there is no common agreement on what constitutes as heavy computation.
   * @throws Exception allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void untested_spec304_requestShouldNotPerformHeavyComputations() throws Exception;
  /**
   * Currently, this test is skipped because there is no common agreement on what constitutes as heavy computation.
   * @throws Exception allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void untested_spec305_cancelMustNotSynchronouslyPerformHeavyCompuatation() throws Exception;
  /**
   * Asks for a short {@code Publisher} (length 3) and verifies that cancelling without requesting anything, then requesting
   * items should result in no signals to be emitted.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>The post-cancellation request pattern is (1, 1, 1).
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>the cancellation indicator flag is properly persisted (may require volatile) and checked as part of the signal emission process.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec306_afterSubscriptionIsCancelledRequestMustBeNops() throws Throwable;
  /**
   * Asks for a single-element {@code Publisher} and verifies that without requesting anything, cancelling the sequence
   * multiple times should result in no signals to be emitted and should result in an thrown exception. 
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 1.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>the cancellation indicator flag is properly persisted (may require volatile) and checked as part of the signal emission process.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec307_afterSubscriptionIsCancelledAdditionalCancelationsMustBeNops() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 10) and issues a {@code request(0)} which should trigger an {@code onError} call
   * with an {@code IllegalArgumentException} and the message containing the string "3.9" (reference to the rule number).
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 10.
   * <p>Note that this test expects the {@code IllegalArgumentException} being signalled through {@code onError}, not by
   * throwing from {@code request()} (which is also forbidden) or signalling the error by any other means (i.e., through the
   * Thread.currentThread().getUncaughtExceptionHandler() for example).
   * <p>Note also that requesting and emission may happen concurrently and honoring this rule may require extra serialization within
   * the {@code Publisher}.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>the {@code Publisher} can emit an {@code onError} in this particular case, even if there was no prior and legal
   * {@code request} call and even if the {@code Publisher} would like to emit items first before emitting an {@code onError}
   * in general.
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec309_requestZeroMustSignalIllegalArgumentException() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 10) and issues a random, negative {@code request()} call which should trigger an {@code onError} call
   * with an {@code IllegalArgumentException} and the message containing the string "3.9" (reference to the rule number).
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 10.
   * <p>Note that this test expects the {@code IllegalArgumentException} being signalled through {@code onError}, not by
   * throwing from {@code request()} (which is also forbidden) or signalling the error by any other means (i.e., through the
   * Thread.currentThread().getUncaughtExceptionHandler() for example).
   * <p>Note also that requesting and emission may happen concurrently and honoring this rule may require extra serialization within
   * the {@code Publisher}.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>the {@code Publisher} can emit an {@code onError} in this particular case, even if there was no prior and legal
   * {@code request} call and even if the {@code Publisher} would like to emit items first before emitting an {@code onError}
   * in general.
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec309_requestNegativeNumberMustSignalIllegalArgumentException() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 20), requests some items (less than the length), consumes one item then
   * cancels the sequence and verifies the publisher emitted at most the requested amount and stopped emitting (or terminated). 
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 20.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>the cancellation indicator flag is properly persisted (may require volatile) and checked as part of the signal emission process.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec312_cancelMustMakeThePublisherToEventuallyStopSignaling() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 3) requests and consumes one element from it, cancels the {@code Subscription}
   * , calls {@code System.gc()} and then checks if all references to the test {@code Subscriber} has been dropped (by checking
   * the {@code WeakReference} has been emptied). 
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>the cancellation indicator flag is properly persisted (may require volatile) and checked as part of the signal emission process.</li>
   * <li>the {@code Publisher} stores the {@code Subscriber} reference somewhere which is then not cleaned up when the {@code Subscriber} is cancelled.
   * Note that this may happen on many code paths in a {@code Publisher}, for example in an emission loop that terminates because of the
   * {@code cancel} signal or because reaching a terminal state. Note also that eagerly nulling {@code Subscriber} references may not be necessary
   * for this test to pass in case there is a self-contained chain of them (i.e., {@code Publisher.subscribe()} creates a chain of fresh
   * {@code Subscriber} instances where each of them only references their downstream {@code Subscriber} thus the chain can get GC'd
   * when the reference to the final {@code Subscriber} is dropped).
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec313_cancelMustMakeThePublisherEventuallyDropAllReferencesToTheSubscriber() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 3) and requests {@code Long.MAX_VALUE} from it, verifying that the
   * {@code Publisher} emits all of its items and completes normally.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec317_mustSupportAPendingElementCountUpToLongMaxValue() throws Throwable;
  /**
   * Asks for a short {@code Publisher} (length 3) and requests {@code Long.MAX_VALUE} from it in total (split across
   * two {@code Long.MAX_VALUE / 2} and one {@code request(1)}), verifying that the
   * {@code Publisher} emits all of its items and completes normally.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than 3.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} implements adding individual request amounts together properly (not overflowing into zero or negative pending request amounts)
   * or not properly deducing the number of emitted items from the pending amount,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec317_mustSupportACumulativePendingElementCountUpToLongMaxValue() throws Throwable;
  /**
   * Asks for a very long {@code Publisher} (up to {@code Integer.MAX_VALUE}), requests {@code Long.MAX_VALUE - 1} after
   * each received item and expects no failure due to a potential overflow in the pending emission count while consuming 
   * 10 items and cancelling the sequence.
   * <p>The test is not executed if {@link org.reactivestreams.tck.PublisherVerification#maxElementsFromPublisher()} is less than {@code Integer.MAX_VALUE}.
   * <p>The request pattern is one {@code request(1)} upfront and ten {@code request(Long.MAX_VALUE - 1)} after.
   * <p>If this test fails, the following could be checked within the {@code Publisher} implementation:
   * <ul>
   * <li>the {@code TestEnvironment} has large enough timeout specified in case the {@code Publisher} has some time-delay behavior,</li>
   * <li>make sure the {@link #required_createPublisher1MustProduceAStreamOfExactly1Element()} and {@link #required_createPublisher3MustProduceAStreamOfExactly3Elements()} tests pass,</li>
   * <li>if the {@code Publisher} implementation actually considers the cumulative request amount it receives,</li>
   * <li>if the {@code Publisher} implements adding individual request amounts together properly (not overflowing into zero or negative pending request amounts)
   * or not properly deducing the number of emitted items from the pending amount,</li>
   * <li>if the {@code Publisher} doesn't lose any {@code request()} signal and the state transition from idle -&gt; emitting or emitting -&gt; keep emitting works properly.</li>
   * </ul>
   * @throws Throwable allow arbitrary exceptions to be thrown by the implementation of this test
   */
  void required_spec317_mustNotSignalOnErrorWhenPendingAboveLongMaxValue() throws Throwable;
}
