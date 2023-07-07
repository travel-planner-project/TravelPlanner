import styles from './Dropdown.module.scss'

type DropdownProps = {
  handleOption: React.Dispatch<React.SetStateAction<string>>
  options: string[]
  selectedOption: string
}

function Dropdown({ handleOption, options, selectedOption }: DropdownProps) {
  const selectOption = (option: string) => {
    handleOption(option)
  }

  return (
    <div className={styles.container}>
      {options.map(opt => (
        <button
          className={opt === selectedOption ? styles.selectedOpt : styles.option}
          type='button'
          key={opt}
          onClick={() => selectOption(opt)}
        >
          {opt}
        </button>
      ))}
    </div>
  )
}

export default Dropdown
