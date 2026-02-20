#!/bin/bash

files=($(git ls-files --others --exclude-standard))
batch_size=20
batch_number=1

for (( i=0; i<${#files[@]}; i+=batch_size )); do
  git add "${files[@]:i:batch_size}"
  git commit -m "feat: Add new chart data (batch $batch_number)"
  git push
  batch_number=$((batch_number + 1))
done
