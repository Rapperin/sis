#!/usr/bin/env bash
set -euo pipefail

BASE="http://localhost:8081"
AUTH="app:app"
COOKIES="cookies.txt"

# helper (CSRF'yi her POST/PUT/DELETE öncesi tazeler)
csrf_refresh() {
  curl -s -c "$COOKIES" "$BASE/api/csrf" | jq -r .token > /tmp/xsrf.txt
  TOKEN=$(cat /tmp/xsrf.txt)
  H=( -H "Content-Type: application/json" -H "X-XSRF-TOKEN: $TOKEN" )
}

echo "==> temizle"
rm -f "$COOKIES" /tmp/xsrf.txt || true

echo "==> 1) INSTRUCTOR CREATE"
csrf_refresh
resp=$(curl -s -i -u "$AUTH" -b "$COOKIES" "${H[@]}" \
  -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@uni.edu"}' \
  "$BASE/api/v1/instructors")
echo "$resp" | head -n1
INSTRUCTOR_ID=$(echo "$resp" | sed -n 's/.*"id":\([0-9]\+\).*/\1/p')

echo "==> 2) COURSE CREATE"
csrf_refresh
resp=$(curl -s -i -u "$AUTH" -b "$COOKIES" "${H[@]}" \
  -d '{"code":"CSE101","name":"Intro to CS","credit":6}' \
  "$BASE/api/v1/courses")
echo "$resp" | head -n1
COURSE_ID=$(echo "$resp" | sed -n 's/.*"id":\([0-9]\+\).*/\1/p')

echo "==> 3) SECTION CREATE"
csrf_refresh
resp=$(curl -s -i -u "$AUTH" -b "$COOKIES" "${H[@]}" \
  -d "{\"courseId\":$COURSE_ID,\"instructorId\":$INSTRUCTOR_ID,\"semester\":\"Fall 2025\",\"capacity\":40,\"schedule\":\"Mon 10:00-12:00 / R-101\"}" \
  "$BASE/api/v1/sections")
echo "$resp" | head -n1
SECTION_ID=$(echo "$resp" | sed -n 's/.*"id":\([0-9]\+\).*/\1/p')

echo "==> 4) STUDENT CREATE"
csrf_refresh
resp=$(curl -s -i -u "$AUTH" -b "$COOKIES" "${H[@]}" \
  -d '{"firstName":"Grace","lastName":"Hopper","email":"grace@uni.edu"}' \
  "$BASE/api/v1/students")
echo "$resp" | head -n1
STUDENT_ID=$(echo "$resp" | sed -n 's/.*"id":\([0-9]\+\).*/\1/p')

echo "==> 5) ENROLLMENT CREATE (mevcut şema: courseId + semester)"
csrf_refresh
curl -s -i -u "$AUTH" -b "$COOKIES" "${H[@]}" \
  -d "{\"studentId\":$STUDENT_ID,\"courseId\":$COURSE_ID,\"semester\":\"Fall 2025\"}" \
  "$BASE/api/v1/enrollments" | head -n1

echo "==> 6) LIST'LER (GET)"
curl -s -i -u "$AUTH" -b "$COOKIES" "$BASE/api/v1/instructors" | head -n1
curl -s -i -u "$AUTH" -b "$COOKIES" "$BASE/api/v1/courses"     | head -n1
curl -s -i -u "$AUTH" -b "$COOKIES" "$BASE/api/v1/sections"    | head -n1
curl -s -i -u "$AUTH" -b "$COOKIES" "$BASE/api/v1/students"    | head -n1
curl -s -i -u "$AUTH" -b "$COOKIES" "$BASE/api/v1/enrollments/by-student/$STUDENT_ID" | head -n1

echo "==> 7) ENROLLMENT DELETE"
csrf_refresh
curl -s -i -u "$AUTH" -b "$COOKIES" "${H[@]}" \
  -X DELETE \
  "$BASE/api/v1/enrollments?studentId=$STUDENT_ID&courseId=$COURSE_ID&semester=Fall%202025" \
  | head -n1

