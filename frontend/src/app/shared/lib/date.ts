export function formatDate(dateString: string): string {
  const date = new Date(dateString);
  const now = new Date();

  // Calculate time difference in milliseconds
  const diffInMs = now.getTime() - date.getTime();

  // Time thresholds in milliseconds
  const msInMinute = 60 * 1000;
  const msInHour = 60 * msInMinute;
  const msInDay = 24 * msInHour;
  const msInMonth = 30 * msInDay;
  const msInYear = 365 * msInDay;

  // If the date is within the last 24 hours
  if (diffInMs < msInDay) {
    if (diffInMs < msInMinute) {
      return `just now`; // Less than a minute
    } else if (diffInMs < msInHour) {
      return `${Math.floor(diffInMs / msInMinute)} minute${
        Math.floor(diffInMs / msInMinute) !== 1 ? 's' : ''
      } ago`; // Minutes ago
    } else {
      return `${Math.floor(diffInMs / msInHour)} hour${
        Math.floor(diffInMs / msInHour) !== 1 ? 's' : ''
      } ago`; // Hours ago
    }
  }

  // If the date is within the last 30 days
  else if (diffInMs < msInMonth) {
    return `${Math.floor(diffInMs / msInDay)} day${
      Math.floor(diffInMs / msInDay) !== 1 ? 's' : ''
    } ago`; // Days ago
  }

  // If the date is within the last 365 days (1 year)
  else if (diffInMs < msInYear) {
    return `${Math.floor(diffInMs / msInMonth)} month${
      Math.floor(diffInMs / msInMonth) !== 1 ? 's' : ''
    } ago`; // Months ago
  }

  // Otherwise return the exact date in the format HH:MM DD/MM/YYYY
  const time = date.toLocaleTimeString('en-US', {
    hour: '2-digit',
    minute: '2-digit',
    hour12: false, // 24-hour format
  });

  const formattedDate = date.toLocaleDateString('en-US', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  });

  return `${time} ${formattedDate}`;
}
