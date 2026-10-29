const { initializeTestEnvironment, assertFails, assertSucceeds } = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

const PROJECT_ID = "demo-no-project";
let testEnv;

before(async () => {
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules: fs.readFileSync("firestore.rules", "utf8"),
      host: "127.0.0.1",
      port: 8085,
    },
  });
});

beforeEach(async () => {
  await testEnv.clearFirestore();
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

test("unauthenticated user cannot write to users collection", async () => {
  const unauthedDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(
    unauthedDb.collection("users").doc("user_123").set({
      userId: "user_123",
      username: "Hacker",
      level: 1,
      xp: 0,
      musizCoins: 0,
      currentStreak: 0,
      totalGamesPlayed: 0,
      totalWins: 0,
    })
  );
});

test("authenticated user can create own profile", async () => {
  const authedDb = testEnv.authenticatedContext("user_123").firestore();
  await assertSucceeds(
    authedDb.collection("users").doc("user_123").set({
      userId: "user_123",
      username: "SimeonPlayer",
      level: 1,
      xp: 100,
      musizCoins: 10,
      currentStreak: 1,
      totalGamesPlayed: 1,
      totalWins: 1,
    })
  );
});

test("authenticated user cannot write to another user profile", async () => {
  const authedDb = testEnv.authenticatedContext("user_123").firestore();
  await assertFails(
    authedDb.collection("users").doc("other_456").set({
      userId: "other_456",
      username: "Impostor",
      level: 1,
      xp: 100,
      musizCoins: 10,
      currentStreak: 1,
      totalGamesPlayed: 1,
      totalWins: 1,
    })
  );
});

test("anyone can read leaderboards", async () => {
  const unauthedDb = testEnv.unauthenticatedContext().firestore();
  await assertSucceeds(unauthedDb.collection("leaderboard").get());
});
