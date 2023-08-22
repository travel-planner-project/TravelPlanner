export function dateFormat(date: Date) {
  const month: number = date.getMonth() + 1
  const day: number = date.getDate()

  const formattedMonth: string = `${month}`
  const formattedDay: string = `${day}`

  return `${date.getFullYear()}년 ${formattedMonth}월 ${formattedDay}일`
}
