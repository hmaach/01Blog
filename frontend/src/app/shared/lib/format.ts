export function formatNumber(num: number): string {
  if (num === null || num === undefined) return '0';
  if (num < 1000) return num.toString();

  const units = ['K', 'M', 'B', 'T'];
  let unitIndex = -1;

  do {
    num /= 1000;
    unitIndex++;
  } while (num >= 1000 && unitIndex < units.length - 1);

  return num % 1 === 0 ? `${num}${units[unitIndex]}` : `${num.toFixed(1)}${units[unitIndex]}`;
}
