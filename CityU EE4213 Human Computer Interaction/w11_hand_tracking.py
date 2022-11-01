import cv2
import numpy as np
import mediapipe as mp
mp_drawing = mp.solutions.drawing_utils
mp_drawing_styles = mp.solutions.drawing_styles
mp_hands = mp.solutions.hands

def draw_item(pos, frame):
    for p in pos:
        cv2.circle(frame, p, 10, (0, 0, 255), thickness=-1, lineType=cv2.FILLED)

def main():
    # For webcam input:
    cap = cv2.VideoCapture(0)
    # For video input:
    # cap = cv2.VideoCapture(video_path)

    # get size of frame in video
    w = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    h = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))

    # initialize item (points) position
    item_pos = np.random.randint(0,h,(3,2))
    with mp_hands.Hands(
            model_complexity=0,
            min_detection_confidence=0.5,
            min_tracking_confidence=0.5) as hands:
        # check if successfully get frame from video
        while cap.isOpened():
            success, image = cap.read()
            if not success:
                print("Ignoring empty camera frame.")
                # If loading a video, use 'break' instead of 'continue'.
                continue

            # To improve performance, optionally mark the image as not writeable to
            # pass by reference.
            image.flags.writeable = False
            image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            results = hands.process(image)

            # Draw the hand annotations on the image.
            image.flags.writeable = True
            image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
            if results.multi_hand_landmarks:
                for hand_landmarks in results.multi_hand_landmarks:
                    mp_drawing.draw_landmarks(
                        image,
                        hand_landmarks,
                        mp_hands.HAND_CONNECTIONS,
                        mp_drawing_styles.get_default_hand_landmarks_style(),
                        mp_drawing_styles.get_default_hand_connections_style())
                # convert output of mediapipe to a 21*3 matrix
                # hand pose is described by 21 key points
                # each point's position is described by a 3 dimensional vector (x, y, z)
                label = np.array([[p.x * w, p.y * h, p.z * w] for p in results.multi_hand_landmarks[0].landmark])
                for i in range(len(item_pos)):
                    # if the distance between index finger tip and one item (point) is less than a threshold
                    # update the position of the item as the position of indix finger tip
                    if sum((label[8,:2] - item_pos[i]) ** 2) < 500:
                        item_pos[i] = label[8,:2]
            draw_item(item_pos, image)
            # Flip the image horizontally for a selfie-view display.
            cv2.imshow('MediaPipe Hands', image)
            if cv2.waitKey(1) & 0xFF == 27:
                break
    cap.release()

if __name__ == '__main__':
    main()

