export function dateFormat(date: Date) {
  const month: number = date.getMonth() + 1
  const day: number = date.getDate()

  const formattedMonth: string = `${month}`
  const formattedDay: string = `${day}`

  return `${date.getFullYear()}년 ${formattedMonth}월 ${formattedDay}일`
}

export function dateFormatDash(date: Date) {
  const month: number = date.getMonth() + 1
  const day: number = date.getDate()

  const formattedMonth: string = month < 10 ? `0${month}` : `${month}`
  const formattedDay: string = day < 10 ? `0${day}` : `${day}`

  return `${date.getFullYear()}-${formattedMonth}-${formattedDay}`
}
